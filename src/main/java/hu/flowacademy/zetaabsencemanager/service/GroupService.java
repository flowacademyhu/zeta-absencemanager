package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Group;
import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.repository.GroupRepository;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;

import hu.flowacademy.zetaabsencemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class GroupService {

  @Autowired
  private GroupRepository groupRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  public Group findOne(@NotNull Long id) {
    return groupRepository.findByIdAndDeletedAtIsNull(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Group not found"));
  }

  public List<Group> findAllGroup() {
    return groupRepository.findAll();
  } // Átírni findAllByDeletedAtIsNull() -ra!!!

  public Group create(@NotNull Group group) {
      if (group.getLeader() != null) {
        group.getLeader().setRole(Roles.LEADER);
      }
      return groupRepository.save(group);
  }

  public Group updateGroup(@NotNull Long id, @NotNull Group group) {
    Group modifyGroup = findOne(id);
    modifyGroup.setName(group.getName());
    modifyGroup.setParentId(group.getParentId());
    modifyGroup.setEmployees(group.getEmployees());
    if (group.getLeader().getGroup().getId() == modifyGroup.getParentId()
        && group.getLeader().getRole() == Roles.EMPLOYEE) {
      modifyGroup.setLeader(group.getLeader());
    }
    modifyGroup.setUpdatedAt(LocalDateTime.now());
    groupRepository.save(modifyGroup);
    return modifyGroup;
  }

  public void delete(@NotNull Long id) {
    Group group = groupRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "The submitted arguments are invalid."));
    List<Group> needToBeModifiedGroups = groupRepository.findAllByParentId(group.getId());
    for (Group g : needToBeModifiedGroups) {
      g.setParentId(null);
      g.setLeader(null);
      groupRepository.save(g);
    }
    List<User> needToBeModifiedEmployees = userRepository.findAllByGroupAndDeletedAtNull(group);
    for (User u : needToBeModifiedEmployees) {
      u.setGroup(null);
      if (u.getRole().equals(Roles.EMPLOYEE)) {
        u.setRole(Roles.ADMIN);
        // userRepository.save(u);
      }
      userRepository.save(u);
    }
    User needToBeModifiedLeader = userService.findOneUser(group.getLeader().getId());
    needToBeModifiedLeader.setRole(Roles.ADMIN);
    userRepository.save(needToBeModifiedLeader);
    group.setEmployees(null);
    group.setLeader(null);
    group.setDeletedAt(LocalDateTime.now());

    groupRepository.save(group);
  }
}
