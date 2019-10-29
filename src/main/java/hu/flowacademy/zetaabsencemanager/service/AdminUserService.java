package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.*;
import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import hu.flowacademy.zetaabsencemanager.repository.GroupRepository;
import hu.flowacademy.zetaabsencemanager.repository.UserRepository;
import hu.flowacademy.zetaabsencemanager.utils.Constants;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class AdminUserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private GroupRepository groupRepository;

  @Autowired
  private AbsenceRepository absenceRepository;

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private UserService userService;

  @Autowired
  private GroupService groupService;

  @Autowired
  private AbsenceService absenceService;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  public User findByEmail(@NotNull String email) {
    return this.userRepository.findByEmailAndDeletedAtNull(email)
        .orElseThrow(() -> {

          return new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.USER_NOT_FOUND);
        });
  }

  public List<User> findAllUser() {
    List<User> users = this.userRepository.findByDeletedAtNull();
    return users;
  }

  public User findOneUser(@NotNull Long id) {
    User user = userRepository.findByIdAndDeletedAtNull(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.USER_NOT_FOUND));
    return user;
  }

  public User saveUser(@NotNull User user) {
    User newUser = User.builder()
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .dateOfBirth(user.getDateOfBirth())
        .dateOfEntry(user.getDateOfEntry())
        .dateOfEndTrial(user.getDateOfEndTrial())
        .email(user.getEmail())
        .group(user.getGroup())
        .position(user.getPosition())
        .password(passwordEncoder.encode("user"))
        .role(Roles.EMPLOYEE)
        .numberOfChildren(user.getNumberOfChildren())
        .extraAbsenceDays(0)
        .otherAbsenceEntitlement(user.getOtherAbsenceEntitlement())
        .createdAt(LocalDateTime.now())
        .build();
    if (user.getExtraAbsenceDays() != null) {
      newUser.setExtraAbsenceDays(user.getExtraAbsenceDays());
      newUser.setExtraAbsencesUpdatedAt(LocalDateTime.now());
    }
    int availableAbsenceDays = absenceService.availableAbsence(newUser);
    int sickLeaveDays = absenceService.availableSickLeave(newUser);
    newUser.setTotalAbsenceDays(availableAbsenceDays);
    newUser.setTotalSickLeaveDays(sickLeaveDays);
    userRepository.save(newUser);
    return newUser;
  }


  public User updateUser(@NotNull Long id, @NotNull User user) {
    User modifyUser = findOneUser(id);
    modifyUser.setLastName(user.getLastName());
    modifyUser.setFirstName(user.getFirstName());
    modifyUser.setDateOfBirth(user.getDateOfBirth());
    modifyUser.setEmail(user.getEmail());
    modifyUser.setDateOfEntry(user.getDateOfEntry());
    modifyUser.setDateOfEndTrial(user.getDateOfEndTrial());
    modifyUser.setGroup(user.getGroup());
    modifyUser.setPosition(user.getPosition());
    modifyUser.setNumberOfChildren(user.getNumberOfChildren());
    modifyUser.setOtherAbsenceEntitlement(user.getOtherAbsenceEntitlement());
    if (!(user.getExtraAbsenceDays().equals(modifyUser.getExtraAbsenceDays()))) {
      modifyUser.setExtraAbsenceDays(user.getExtraAbsenceDays());
      modifyUser.setExtraAbsencesUpdatedAt(LocalDateTime.now());
    }
    modifyUser.setUpdatedAt(LocalDateTime.now());
    modifyUser.setUpdatedBy(authenticationService.getCurrentUser());
    int availableAbsenceDays = absenceService.availableAbsence(modifyUser);
    int sickLeaveDays = absenceService.availableSickLeave(modifyUser);
    modifyUser.setTotalAbsenceDays(availableAbsenceDays);
    modifyUser.setTotalSickLeaveDays(sickLeaveDays);
    userRepository.save(modifyUser);
    return modifyUser;
  }


  public void delete(@NotNull Long id) {
    User mod = findOneUser(id);
    List<Group> groupList = groupService.findAllGroup();
    mod.setRole(Roles.INACTIVE);
    mod.setDeletedBy(authenticationService.getCurrentUser());
    mod.setGroup(null);
    mod.setDeletedAt(LocalDateTime.now());
    if (mod.getGroup() != null) {
      Group modifyGroup = groupService.findOne(mod.getGroup().getId());
      for (int i = 0; i < modifyGroup.getEmployees().size(); i++) {
        if (modifyGroup.getEmployees().size() > 0 && modifyGroup.getEmployees().get(i).getId()
            .equals(id)) {
          modifyGroup.getEmployees().remove(modifyGroup.getEmployees().get(i));
          modifyGroup.setUpdatedAt(LocalDateTime.now());
          groupRepository.save(modifyGroup);
        }
      }
    }
    for (Group g : groupList) {
      if (g.getLeader() != null && g.getLeader().getId().equals(id)) {
        g.setLeader(null);
        g.setUpdatedAt(LocalDateTime.now());
        groupRepository.save(g);
      }
    }
    List<Absence> needToBeModifiedAbsences = absenceRepository.findByReporterAndDeletedAtNull(mod);
    for (Absence a : needToBeModifiedAbsences) {
      a.setStatus(Status.REJECTED);
      a.setUpdatedBy(authenticationService.getCurrentUser());
      a.setUpdatedAt(LocalDateTime.now());
      absenceRepository.save(a);
    }
    userRepository.save(mod);
  }


  public List<User> findAllLeader() {
    return userRepository.findByRoleAndDeletedAtNull(Roles.LEADER);
  }

  public List<User> findAllEmployeesByGroupIsNull() {
    List<User> users = findAllUser();
    List<User> employees = new ArrayList<>();
    for (User u : users) {
      if (u.getRole().equals(Roles.EMPLOYEE) && u.getGroup() == null) {
        employees.add(u);
      }
    }
    return employees;
  }

  public List<User> findAllEmployeesByGroupId(Long groupId) {
    Group group = groupService.findOne(groupId);
    List<User> users = userRepository.findAllByGroupAndDeletedAtNull(group);
    List<User> employees = new ArrayList<>();
    for (User u : users) {
      if (u.getRole().equals(Roles.EMPLOYEE)) {
        employees.add(u);
      }
    }
    return employees;
  }
}