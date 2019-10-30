package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.Group;
import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.Status;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.model.validator.UserValidator;
import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import hu.flowacademy.zetaabsencemanager.repository.GroupRepository;
import hu.flowacademy.zetaabsencemanager.repository.UserRepository;
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

  @Autowired
  private UserValidator userValidator;

  public User findByEmail(@NotNull String email) {
    return this.userRepository.findByEmailAndDeletedAtNull(email)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
  }

  public List<User> findAllUser() {
    if (this.authenticationService.hasRole(Roles.ADMIN)) {
      return this.userRepository.findByDeletedAtNull();
    } else {
      Group group = this.groupRepository
          .findByLeaderAndDeletedAtNull(this.authenticationService.getCurrentUser())
          .orElseThrow(
              () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                  "Group not found, you are not leader of any group."));
      return this.userRepository.findByGroupAndDeletedAtNull(group);
    }
  }

  public User findOneUser(@NotNull Long id) {
    if (this.authenticationService.hasRole(Roles.ADMIN)) {
      return userRepository.findByIdAndDeletedAtNull(id)
          .orElseThrow(
              () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found."));
    } else {
      User user = userRepository.findByIdAndDeletedAtNull(id).orElseThrow(
          () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found."));
      if (this.userValidator.IsInLeadersGroup(this.authenticationService.getCurrentUser(), user)) {
        return user;
      } else {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not in your group.");
      }
    }
  }

  public User saveUser(@NotNull User user) {
    if (this.authenticationService.hasRole(Roles.ADMIN)) {
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
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only admin can create user.");
    }
  }


  public User updateUser(@NotNull Long id, @NotNull User user) {
    if (this.authenticationService.hasRole(Roles.ADMIN)) {
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
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
          "You can only modify your profile.");
    }
  }


  public void delete(@NotNull Long id) {
    if (this.authenticationService.hasRole(Roles.ADMIN)) {
      User mod = findOneUser(id);
      if (!mod.getRole().equals(Roles.LEADER)) {

        List<Absence> needToBeModifiedAbsences = absenceRepository
            .findByReporterAndDeletedAtNull(mod);
        for (Absence a : needToBeModifiedAbsences) {         // a törölt User absence-einek státuszát REJECTED-dé tesszük
          a.setStatus(Status.REJECTED);
          a.setUpdatedBy(authenticationService.getCurrentUser());
          a.setUpdatedAt(LocalDateTime.now());
          absenceRepository.save(a);
        }
        mod.setRole(Roles.INACTIVE);    // a törölt user role-ját
        mod.setDeletedBy(authenticationService.getCurrentUser());
        mod.setGroup(null);         // null-ozzuk a User group adattagját
        mod.setDeletedAt(LocalDateTime.now());
        userRepository.save(mod);
      } else {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "You can't delete leader's profile.");
      }
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
          "You can only delete your profile.");
    }
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