package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.Status;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import hu.flowacademy.zetaabsencemanager.repository.GroupRepository;
import hu.flowacademy.zetaabsencemanager.repository.UserRepository;
import hu.flowacademy.zetaabsencemanager.utils.Constants;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class UserService {


  @Autowired
  @Lazy
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private UserRepository userRepository;


  @Autowired
  private AbsenceRepository absenceRepository;

  @Autowired
  private GroupService groupService;

  @Autowired
  private GroupRepository groupRepository;

  @Autowired
  private AuthenticationService authenticationService;


  public User findByEmail(String email) {
    return this.userRepository.findByEmailAndDeletedAtNull(email)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.USER_NOT_FOUND));
  }

  public User findOneUser(Long id) {
    User user = this.userRepository.findByIdAndDeletedAtNull(id)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.USER_NOT_FOUND));
    return user;
  }

  public User updateUser(@NotNull Long id, @NotNull User user) {
    User modifyUser = findOneUser(id);
    modifyUser.setLastName(user.getLastName());
    modifyUser.setFirstName(user.getFirstName());
    modifyUser.setEmail(user.getEmail());
    modifyUser.setUpdatedAt(LocalDateTime.now());
    userRepository.save(modifyUser);
    modifyUser.setPassword(null);
    return modifyUser;
  }

  public void delete(@NotNull Long id) {
    if (id.equals(authenticationService.getCurrentUser().getId())) {
      User deleted = findOneUser(id);
      if (!deleted.getRole().equals(Roles.LEADER)) {
        deleted.setRole(Roles.INACTIVE);
        deleted.setGroup(null);
        deleted.setDeletedAt(LocalDateTime.now());
        userRepository.save(deleted);
      } else {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "You can't delete your profile, because your role is leader.");
      }
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
          "You can only delete your profile.");
    }
  }

  public User changePassword(@NotNull String firstPassword, @NotNull String secondPassword,
      @NotNull String oldPassword) {
    User modifyUser = authenticationService.getCurrentUser();
    String currentPassword = modifyUser.getPassword();
    if ((passwordEncoder.matches(oldPassword, currentPassword)) && (firstPassword
        .equals(secondPassword))) {
      modifyUser.setPassword(passwordEncoder.encode(firstPassword));
      return userRepository.save(modifyUser);
    } else {
      throw new IllegalArgumentException(Constants.NOT_EQUAL_PWS);
    }
  }

}
