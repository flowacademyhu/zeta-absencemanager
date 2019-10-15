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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public List<Group> findAllGroup() {
        return groupRepository.findAllByDeletedAtIsNull();
    }

    public Group findOne(@NotNull Long id) {
        return groupRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Group not found"));
    }

    public Group updateGroup(@NotNull Long id, @NotNull Group group) {
        Group modifyGroup = findOne(id);
        if (StringUtils.isEmpty(group.getName()) || group.getParentId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid.");
        } else {
            modifyGroup.setName(group.getName());
            modifyGroup.setParentId(group.getParentId());
            modifyGroup.setEmployees(group.getEmployees());
            modifyGroup.setLeaders(group.getLeaders());
            groupRepository.save(modifyGroup);
            return modifyGroup;
        }
    }

    public Group create(@NotNull Group group) {
        if (StringUtils.isEmpty(group.getName()) || group.getParentId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid.");
        }
        return groupRepository.save(group);
    }

    public void delete(@NotNull Long id) {
        Group group = groupRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid."));
        group.setDeletedAt(LocalDateTime.now());
        groupRepository.save(group);
    }

}
