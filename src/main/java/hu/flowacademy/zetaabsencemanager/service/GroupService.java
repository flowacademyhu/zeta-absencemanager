package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Group;
import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.repository.GroupRepository;
import hu.flowacademy.zetaabsencemanager.repository.UserRepository;
import hu.flowacademy.zetaabsencemanager.utils.Constants;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
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

  @Autowired
  private AuthenticationService authenticationService;

  public Group findOne(@NotNull Long id) {
    return groupRepository.findByIdAndDeletedAtIsNull(id)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.GROUP_NOT_FOUND));
  }

  public List<Group> findAllGroup() {
    return groupRepository.findAllByDeletedAtIsNull();
  }

  public Group create(@NotNull Group group) {
    User modifyUser = userService.findOneUser(group.getLeader().getId());
    modifyUser.setRole(Roles.LEADER);
    userRepository.save(modifyUser);
    return groupRepository.save(group);
  }

  public Group updateGroup(@NotNull Long id, @NotNull Group group) {
    Group modifyGroup = findOne(id);
    modifyGroup.setName(group.getName());
    modifyGroup.setParentId(group.getParentId());
    if (!modifyGroup.getLeader().getId().equals(group.getLeader().getId())) {
      User oldLeader = userService.findOneUser(modifyGroup.getLeader().getId());
      if (oldLeader.getRole() != Roles.ADMIN) {
        oldLeader.setRole(Roles.EMPLOYEE);
        userRepository.save(oldLeader);
      }
      User newLeader = userService.findOneUser(group.getLeader().getId());
      if (newLeader.getRole() != Roles.ADMIN) {
        newLeader.setRole(Roles.LEADER);
        userRepository.save(newLeader);
      }
    }
    modifyGroup.setLeader(group.getLeader());
    modifyGroup.setUpdatedAt(LocalDateTime.now());
    groupRepository.save(modifyGroup);
    return modifyGroup;
  }

  public void delete(@NotNull Long id) {
    Group group = groupRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            Constants.INVALID_ARGUMENTS));

    if (CollectionUtils.isEmpty(group.getEmployees())) {
      List<Group> childGroups = groupRepository.findAllByParentId(group.getId());
      for (Group g : childGroups) {
        g.setParentId(null);
        g.setUpdatedAt(LocalDateTime.now());
        groupRepository.save(g);
      }
      User needToBeModifiedLeader = userService.findOneUser(group.getLeader().getId());
      needToBeModifiedLeader.setRole(Roles.EMPLOYEE);
      needToBeModifiedLeader.setUpdatedAt(LocalDateTime.now());
      userRepository.save(needToBeModifiedLeader);

      group.setLeader(null);
      group.setDeletedAt(LocalDateTime.now());

      groupRepository.save(group);
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "You can't delete a group, while it has employees.");
    }
  }
}
