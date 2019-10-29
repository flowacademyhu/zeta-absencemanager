package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.repository.UserRepository;
import java.time.LocalDateTime;
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
  private AuthenticationService authenticationService;

  public User findByEmail(String email) {
    return this.userRepository.findByEmailAndDeletedAtNull(email)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
  }

  public User findOneUser(Long id) {
    User user = this.userRepository.findByIdAndDeletedAtNull(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
    return user;
  }

  public User updateUser(@NotNull Long id, @NotNull User user) {
    User modifyUser = findOneUser(id);
    modifyUser.setLastName(user.getLastName());
    modifyUser.setFirstName(user.getFirstName());
    modifyUser.setEmail(user.getEmail());
    modifyUser.setUpdatedAt(LocalDateTime.now());
    // modifyUser.setUpdatedBy(authenticationService.getCurrentUser()); not working cuz of dataloder calling it without currentuser TODO
    userRepository.save(modifyUser);
    modifyUser.setPassword(null);
    return modifyUser;
  }

  public User changePassword(@NotNull String firstPassword, @NotNull String secondPassword,
      @NotNull String oldPassword) {
    User modifyUser = authenticationService.getCurrentUser();
    String currentPassword = modifyUser.getPassword();
    if ((passwordEncoder.matches(oldPassword, currentPassword)) && (firstPassword
        .equals(secondPassword))) {
      modifyUser.setPassword(passwordEncoder.encode(firstPassword));
      userRepository.save(modifyUser);
    } else {
      throw new IllegalArgumentException("The submitted passwords are different.");
    }
    return modifyUser;
  }

  public void delete(@NotNull Long id) {
    User deleted = findOneUser(id);
    deleted.setRole(Roles.INACTIVE);
    deleted.setDeletedBy(authenticationService.getCurrentUser());
    deleted.setDeletedAt(LocalDateTime.now());
    userRepository.save(deleted);
  }
}
