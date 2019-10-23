package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.repository.UserRepository;
import java.time.LocalDateTime;
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
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User findByEmail(@NotNull String email) {
        return this.userRepository.findByEmailAndDeletedAtNull(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
    }

    public List<User> findAllUser() {
        List<User> users = this.userRepository.findByDeletedAtNull();
        for (User user : users) {
            user.setPassword(null);
        }
        return users;
    }

    public User findOneUser(@NotNull Long id) {
        User user = userRepository.findByIdAndDeletedAtNull(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found."));
        user.setPassword(null);
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
                .otherAbsenceEntitlement(user.getOtherAbsenceEntitlement())
                .createdAt(LocalDateTime.now())
                .build();
        if (user.getExtraAbsenceDays() != null) {
            newUser.setExtraAbsenceDays(user.getExtraAbsenceDays());
            newUser.setExtraAbsencesUpdatedAt(LocalDateTime.now());
        }
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
        if (!user.getExtraAbsenceDays().equals(modifyUser.getExtraAbsenceDays())) {
            modifyUser.setExtraAbsenceDays(user.getExtraAbsenceDays());
            modifyUser.setExtraAbsencesUpdatedAt(LocalDateTime.now());
        }
        modifyUser.setUpdatedAt(LocalDateTime.now());
        modifyUser.setUpdatedBy(authenticationService.getCurrentUser());
        userRepository.save(modifyUser);
        modifyUser.setPassword(null);
        return modifyUser;
    }

    public void delete(@NotNull Long id) {
        User mod = findOneUser(id);
        mod.setRole(Roles.INACTIVE);
        mod.setDeletedAt(LocalDateTime.now());
        mod.setDeletedBy(authenticationService.getCurrentUser());
        updateUser(id, mod);
    }
}