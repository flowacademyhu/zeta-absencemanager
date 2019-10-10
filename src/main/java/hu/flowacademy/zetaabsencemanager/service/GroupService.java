package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Group;
import hu.flowacademy.zetaabsencemanager.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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
  
    public Optional<Group> findOne(Long id){
        return groupRepository.findById(id);
    }

    public Group create(Group group) {
        if (group.getId() != null || group.getName().equals("") || group.getName() == null ||
                group.getLeaders().size() == 0 || group.getDepartment() == null || group.getEmployees().size() == 0)
        {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid.");
        }
        return groupRepository.save(group);
    }

}
