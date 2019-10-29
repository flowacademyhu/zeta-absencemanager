package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.*;
import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import hu.flowacademy.zetaabsencemanager.repository.GroupRepository;
import hu.flowacademy.zetaabsencemanager.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

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
            modifyUser.setPassword(passwordEncoder.encode(firstPassword));
            userRepository.save(modifyUser);
            System.out.println("ÚJ JELSZÓ: " + modifyUser.getPassword());
            // modifyUser.setPassword(null);
        }
        return modifyUser;
    }

    public void delete(@NotNull Long id) {
        User deleted = findOneUser(id);
        Group modifyGroup = groupService.findOne(deleted.getGroup().getId());
        Group modifyLeadedGroup;
        List<Group> groupList = groupService.findAllGroup();
        deleted.setRole(Roles.INACTIVE);
        deleted.setDeletedBy(authenticationService.getCurrentUser());
        deleted.setGroup(null);
        deleted.setDeletedAt(LocalDateTime.now());
        for (User e : modifyGroup.getEmployees()) {
            if (modifyGroup.getEmployees().size() > 0 && e.getId().equals(id)) {
                modifyGroup.getEmployees().remove(e);
                modifyGroup.setUpdatedAt(LocalDateTime.now());
                groupRepository.save(modifyGroup);
            }
        }
        for (Group g : groupList) {
            if (g.getLeader() != null && g.getLeader().getId().equals(id))  {
                modifyLeadedGroup = g;
                modifyLeadedGroup.setLeader(null);
                modifyLeadedGroup.setUpdatedAt(LocalDateTime.now());
                groupRepository.save(modifyLeadedGroup);
            }
        }
        List <Absence> needToBeModifiedAbsences = absenceRepository.findByReporterAndDeletedAtNull(deleted);
        for (Absence a : needToBeModifiedAbsences) {
            a.setStatus(Status.REJECTED);
            a.setReporter(null);
            a.setUpdatedAt(LocalDateTime.now());
            a.setUpdatedBy(authenticationService.getCurrentUser());
            absenceRepository.save(a);
        }

        userRepository.save(deleted);
    }

}
