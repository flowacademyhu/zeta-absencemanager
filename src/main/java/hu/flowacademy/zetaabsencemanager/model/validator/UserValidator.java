package hu.flowacademy.zetaabsencemanager.model.validator;

import hu.flowacademy.zetaabsencemanager.model.Group;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserValidator {

  @Autowired
  private GroupRepository groupRepository;

  public boolean IsInLeadersGroup(User leader, User user) {
    Group leadersGroup = this.groupRepository.findByLeaderAndDeletedAtNull(leader)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Group not found."));
    if (leadersGroup.getEmployees().contains(user)) {
      return true;
    }
    return false;
  }
}
