package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "user not found");
        }
        String email = auth.getName();
        return findByEmail(email);
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmailAndDeletedAtNull(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
    }

    public User findOneUser(Long id) {
        return this.userRepository.findByIdAndDeletedAtNull(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
    }

    public User updateUser(@NotNull Long id, @NotNull User user) {
        User modifyUser = findOneUser(id);
        if (modifyUser == null
                || StringUtils.isEmpty(user.getFirstName())
                || StringUtils.isEmpty(user.getLastName())
                || StringUtils.isEmpty(user.getPassword())
                || user.getDateOfBirth() == null
                || StringUtils.isEmpty(user.getEmail())
                || user.getDateOfEntry() == null
                || user.getDateOfEndTrial() == null
                || user.getIsOnTrial() == null
                || user.getGroup() == null
                || StringUtils.isEmpty(user.getPosition())
                || user.getRole() == null
                || user.getNumberOfChildren() == null
                || user.getOtherAbsenceEnt() == null
        ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid.");
        } else {
            modifyUser.setLastName(user.getLastName());
            modifyUser.setFirstName(user.getFirstName());
            modifyUser.setPassword(user.getPassword());
            modifyUser.setEmail(user.getEmail());
            modifyUser.setUpdatedAt(LocalDateTime.now());
            // TODO modifyUser.setUpdatedBy(getCurrentUser());
            userRepository.save(user);
            return modifyUser;
        }
    }


    public void delete(@NotNull Long id) {
        User deleted = findOneUser(id);
        deleted.setDeletedAt(LocalDateTime.now());
        // TODO modifyUser.setDeletedBy(getCurrentUser())
        updateUser(id, deleted);
    }

}
