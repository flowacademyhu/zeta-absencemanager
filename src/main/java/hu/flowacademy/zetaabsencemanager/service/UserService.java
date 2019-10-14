package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email).filter(u -> u.getDeletedAt() == null).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
    }

    public User findOneUser(Long id) {
        return this.userRepository.findById(id).filter(u -> u.getDeletedAt() == null).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
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
                || CollectionUtils.isEmpty(user.getDepartments())
                || CollectionUtils.isEmpty(user.getGroups())
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
            userRepository.save(user);
            return modifyUser;
        }
    }


    public void delete(@NotNull Long id) {
        User mod = findOneUser(id);
        mod.setDeletedAt(LocalDateTime.now());
        updateUser(id, mod);
    }

}
