package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Group;
import hu.flowacademy.zetaabsencemanager.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
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

    public Group updateGroup(Long id, @NotNull Group group) {
        Group modifyGroup = findOneGroup(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Group not found"));
        if (group.getDepartment() == null ||
                StringUtils.isEmpty(group.getName()) ||
                CollectionUtils.isEmpty(group.getEmployees()) ||
                CollectionUtils.isEmpty(group.getLeaders())) {
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
