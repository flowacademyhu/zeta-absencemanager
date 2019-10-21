package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    public User findByEmail(String email) {
        return this.userRepository.findByEmailAndDeletedAtNull(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
    }

    public User findOneUser(Long id) {
        User user = this.userRepository.findByIdAndDeletedAtNull(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
        user.setPassword(null);
        return user;
    }

    public User updateUser(@NotNull Long id, @NotNull User user) {
        User modifyUser = findOneUser(id);
        if (modifyUser == null
                || StringUtils.isEmpty(user.getFirstName())
                || StringUtils.isEmpty(user.getLastName())
                || StringUtils.isEmpty(user.getEmail())
        ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid.");
        } else {
            modifyUser.setLastName(user.getLastName());
            modifyUser.setFirstName(user.getFirstName());
            modifyUser.setEmail(user.getEmail());
            modifyUser.setUpdatedAt(LocalDateTime.now());
            //modifyUser.setUpdatedBy(getCurrentUser());
            userRepository.save(modifyUser);
            modifyUser.setPassword(null);
            return modifyUser;
        }
    }

    public void delete(@NotNull Long id) {
        User deleted = findOneUser(id);
        deleted.setDeletedAt(LocalDateTime.now());
        deleted.setRole(Roles.INACTIVE);
        //deleted.setDeletedBy(getCurrentUser());
        updateUser(id, deleted);
    }
}
