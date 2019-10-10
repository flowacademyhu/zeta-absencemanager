package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public List<User> findAllUser() {
        return this.userRepository.findAll();
    }

    public Optional<User> findOneUser(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User user) {
        User modifyUser=findOneUser(id).orElse(null);
        if (modifyUser == null
                || user.getFirstName() == null
                || user.getFirstName().equals("")
                || user.getLastName() == null
                || user.getLastName().equals("")
                ||user.getPassword()==null
                || user.getPassword().equals("")
                || user.getDateOfBirth() == null
                || user.getEmail() == null
                || user.getEmail().equals("")
                || user.getDateOfEntry() == null
                || user.getDateOfEndTrial() == null
                || user.getIsOnTrial() == null
                || user.getDepartments() == null
                || user.getDepartments().size() == 0
                || user.getGroups() == null
                || user.getGroups().size() == 0
                || user.getPosition() == null
                || user.getPosition().equals("")
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
}
