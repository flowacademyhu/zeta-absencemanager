package hu.flowacademy.zetaabsencemanager.service;


import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.Group;
import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.Status;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.model.validator.AbsenceValidator;
import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import hu.flowacademy.zetaabsencemanager.repository.GroupRepository;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.sberned.statemachine.StateMachine;

@Service
@Transactional
public class AdminAbsenceService {

  @Autowired
  private AbsenceRepository absenceRepository;

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private AbsenceValidator absenceValidator;

  @Autowired
  private GroupRepository groupRepository;

  @Autowired
  private ApplicationEventPublisher publisher;

  @Autowired
  private StateMachine<Absence, Status, Long> stateMachine;


  public Set<User> getEmployees(Group g, Set<User> employees) {
    employees.addAll(g.getLeaders());
    employees.addAll(g.getEmployees());
    groupRepository.findAllByParentId(g.getId())
        .forEach(child -> getEmployees(child, employees));
    return employees;
  }

  public List<Absence> findAllAbsence() {
    if (this.authenticationService.hasRole(Roles.ADMIN)) {
      return this.absenceRepository.findAll();
    } else {
      return getEmployees(this.authenticationService.getCurrentUser().getGroup(),
          new HashSet<>()).stream().flatMap(user -> user.getAbsences().stream()).distinct()
          .collect(Collectors.toList());
    }
  }

  public Absence findOne(@NotNull Long id) {
    User current = authenticationService.getCurrentUser();
    Absence foundAbsence = absenceRepository.findById(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "The submitted arguments are invalid."));
    Set<User> employees = getEmployees(current.getGroup(), new HashSet<>());
    if (this.authenticationService.hasRole(Roles.ADMIN) || (
        this.authenticationService.hasRole(Roles.LEADER) && employees
            .contains(foundAbsence.getReporter()))) {
      return foundAbsence;
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Can not access data");
    }
  }

  public Absence create(Absence absence) {
    this.absenceValidator.validateAbsenceSave(absence);
    User current = authenticationService.getCurrentUser();
    absence.setCreatedAt(LocalDateTime.now());
    absence.setCreatedBy(authenticationService.getCurrentUser());
    // TODO absence.setAssignee();
    absence.setStatus(Status.OPEN);
    Set<User> employees = getEmployees(current.getGroup(), new HashSet<>());
    if (this.authenticationService.hasRole(Roles.ADMIN) || (
        this.authenticationService.hasRole(Roles.LEADER) && employees
            .contains(absence.getReporter()))) {
      return absenceRepository.save(absence);
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Can not access data");
    }
  }

  public Absence update(@NotNull Long id, @NotNull Absence absence) {
    this.absenceValidator.validateAbsenceSave(absence);
    Absence modifyAbsence = absenceRepository.findById(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "The submitted arguments are invalid."));
    User current = authenticationService.getCurrentUser();
    Set<User> employees = getEmployees(current.getGroup(), new HashSet<>());
    if (this.authenticationService.hasRole(Roles.ADMIN) || (
        this.authenticationService.hasRole(Roles.LEADER) && employees
            .contains(modifyAbsence.getReporter()))) {
      modifyAbsence.setType(absence.getType());
      modifyAbsence.setBegin(absence.getBegin());
      modifyAbsence.setEnd(absence.getEnd());
      modifyAbsence.setReporter(absence.getReporter());
      modifyAbsence.setAssignee(absence.getAssignee());
      // modifyAbsence.setStatus(absence.getStatus());
      var stateChange = stateMachine
          .changeState(List.of(absence.getId()), absence.getState(), new Object())
          .get(absence.getId()).isDone();
      System.out.println(stateChange);
      if (!stateChange) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status change.");
      }
      /*publisher.publishEvent(new StateChangedEvent<>(
          absence.getId(), absence.getState()));*/

      modifyAbsence.setUpdatedAt(LocalDateTime.now());
      modifyAbsence.setUpdatedBy(authenticationService.getCurrentUser());
      absenceRepository.save(modifyAbsence);
      return modifyAbsence;
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Can not access data");
    }
  }

  public void delete(@NotNull Long id) {
    Absence deleted = findOne(id);
    deleted.setDeletedAt(LocalDateTime.now());
    deleted.setDeletedBy(authenticationService.getCurrentUser());
    update(id, deleted);
  }

}