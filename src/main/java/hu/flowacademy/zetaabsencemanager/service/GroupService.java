package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Group;
import hu.flowacademy.zetaabsencemanager.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public List<Group> findAllGroup() {
        return groupRepository.findAll();
    }

    public Optional<Group> findOneGroup(Long id) {
        return groupRepository.findById(id);
    }

    public Group updateGroup(Long id, Group group) {
        Group modifyGroup = findOneGroup(id).orElse(null);
        if (modifyGroup == null ||
                group.getDepartment() == null ||
                group.getName() == null ||
                group.getName().equals("") ||
                group.getEmployees() == null ||
                group.getEmployees().size() == 0 ||
                group.getLeaders() == null ||
                group.getLeaders().size() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid.");
        } else {
            modifyGroup.setName(group.getName());
            modifyGroup.setDepartment(group.getDepartment());
            modifyGroup.setEmployees(group.getEmployees());
            modifyGroup.setLeaders(group.getLeaders());
            groupRepository.save(modifyGroup);
            return modifyGroup;
        }
    }
}
