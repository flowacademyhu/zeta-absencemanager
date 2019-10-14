package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AdminUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User findByEmail(@NotNull String email) {
        return this.userRepository.findByEmail(email).filter(user -> user.getDeletedAt() == null).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
    }

    public List<User> findAllUser() {
        return this.userRepository.findAll();
    }

    public User findOneUser(@NotNull Long id) {
        return userRepository.findById(id).filter(user -> user.getDeletedAt() == null).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
    }

    public User updateUser(@NotNull Long id, @NotNull User user) {
        User modifyUser = findOneUser(id);
        if (StringUtils.isEmpty(user.getFirstName())
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
            modifyUser.setDateOfBirth(user.getDateOfBirth());
            modifyUser.setEmail(user.getEmail());
            modifyUser.setDateOfEntry(user.getDateOfEntry());
            modifyUser.setDateOfEndTrial(user.getDateOfEndTrial());
            modifyUser.setIsOnTrial(user.getIsOnTrial());
            modifyUser.setDepartments(user.getDepartments());
            modifyUser.setGroups(user.getGroups());
            modifyUser.setPosition(user.getPosition());
            modifyUser.setRole(user.getRole());
            modifyUser.setNumberOfChildren(user.getNumberOfChildren());
            modifyUser.setOtherAbsenceEnt(user.getOtherAbsenceEnt());
            userRepository.save(user);
            return modifyUser;
        }

    }

    public void delete(@NotNull Long id) {
        User mod = findOneUser(id);
        mod.setDeletedAt(LocalDateTime.now());
        updateUser(id, mod);
    }

    public User saveUser(@NotNull User user) {
        if (StringUtils.isEmpty(user.getFirstName())
                || StringUtils.isEmpty(user.getLastName())
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
            User newUser = User.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .dateOfBirth(user.getDateOfBirth())
                    .dateOfEntry(user.getDateOfEntry())
                    .dateOfEndTrial(user.getDateOfEndTrial())
                    .isOnTrial(user.getIsOnTrial())
                    .email(user.getEmail())
                    .groups(user.getGroups())
                    .departments(user.getDepartments())
                    .position(user.getPosition())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .role(user.getRole())
                    .numberOfChildren(user.getNumberOfChildren())
                    .otherAbsenceEnt(user.getOtherAbsenceEnt())
                    .build();
            userRepository.save(newUser);
            return newUser;
        }
    }

}