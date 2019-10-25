package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.repository.UserRepository;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class UserService {

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
        user.setPassword(null);
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

    public User changePassword(@NotNull Long id, @NotNull String firstPassword, @NotNull String secondPassword) {
        User modifyUser = findOneUser(id);
        if (!firstPassword.equals(secondPassword)) {
                throw new IllegalArgumentException("The submitted passwords are different.");
        } else {
            modifyUser.setPassword(firstPassword);
            userRepository.save(modifyUser);
            System.out.println("ÚJ JELSZÓ: " + modifyUser.getPassword());
            // modifyUser.setPassword(null);
        }
        return modifyUser;
    }

    public void delete(@NotNull Long id) {
        User deleted = findOneUser(id);
        deleted.setDeletedAt(LocalDateTime.now());
        deleted.setRole(Roles.INACTIVE);
        deleted.setDeletedBy(authenticationService.getCurrentUser());
        updateUser(id, deleted);
    }
}