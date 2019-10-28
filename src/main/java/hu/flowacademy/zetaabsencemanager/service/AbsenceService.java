package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.Status;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.model.validator.AbsenceValidator;
import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.sberned.statemachine.state.StateChangedEvent;

@Service
@Transactional
public class AbsenceService {

  @Autowired
  private AbsenceRepository absenceRepository;

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private AbsenceValidator absenceValidator;

  @Autowired
  private ApplicationEventPublisher publisher;

  public Absence findOne(@NotNull Long id) {
    Absence absence = absenceRepository.findByIdAndDeletedAtNull(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Absence not found"));
    if (!absence.getReporter().getId().equals(authenticationService.getCurrentUser().getId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Absence not found");
    }
    return absence;
  }

  public List<Absence> findAll() {
    User current = authenticationService.getCurrentUser();
    return absenceRepository.findByReporterAndDeletedAtNull(current);
  }

  public Absence create(@NotNull Absence absence) {
    this.absenceValidator.validateAbsenceSave(absence);
    absence.setReporter(authenticationService.getCurrentUser());
    absence.setAssignee(authenticationService.getCurrentUser().getGroup().getLeader());
    absence.setCreatedAt(LocalDateTime.now());
    absence.setCreatedBy(authenticationService.getCurrentUser());
    absence.setStatus(Status.OPEN);
    return absenceRepository.save(absence);
  }

  public Absence update(@NotNull Long id, @NotNull Absence absence) {
    Absence modifyAbsence = absenceRepository.findByIdAndDeletedAtNull(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "The submitted arguments are invalid."));
    if (!absence.getReporter().getId().equals(authenticationService.getCurrentUser().getId())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
          "You can only modify your absences");
    }
    modifyAbsence.setType(absence.getType());
    modifyAbsence.setBegin(absence.getBegin());
    modifyAbsence.setEnd(absence.getEnd());
    modifyAbsence.setDuration(absence.getDuration());
    modifyAbsence.setSummary(absence.getSummary());
    modifyAbsence.setReporter(absence.getReporter());
    modifyAbsence.setAssignee(absence.getAssignee());
    modifyAbsence.setStatus(absence.getStatus());
    publisher.publishEvent(new StateChangedEvent<>(
        absence.getId(), absence.getState()));
    modifyAbsence.setUpdatedAt(LocalDateTime.now());
    modifyAbsence.setUpdatedBy(authenticationService.getCurrentUser());
    absenceRepository.save(modifyAbsence);
    return modifyAbsence;
  }

  public void delete(@NotNull Long id) {
    Absence deleted = findOne(id);
    deleted.setDeletedAt(LocalDateTime.now());
    deleted.setDeletedBy(authenticationService.getCurrentUser());
    update(id, deleted);
  }

  public int availableAbsence(@NotNull User user) {
    int calculatedAbsence = 0;
    int allAbsence = 20;
    int[] borders = {25, 28, 31, 33, 35, 37, 39, 41, 43, 45};
    double multiplier;
    int age = LocalDate.now().getYear() - user.getDateOfBirth().getYear();
    if (user.getDateOfEntry().getYear() == LocalDate.now().getYear()) {
      int restDays = 365 - user.getDateOfEntry().getDayOfYear();
      multiplier = restDays / 365;
      System.out.println("Multiplier:" +multiplier);
    } else {
      multiplier = 1;
    }
    for (int i = 0; i < borders.length; i++) {
      if (age >= borders[i]) {
        allAbsence = allAbsence+1;
      }
    }
    switch (user.getNumberOfChildren()) {
      case 0:
        allAbsence = allAbsence+0;
        break;
      case 1:
        allAbsence = allAbsence + 2;
        break;
      case 2:
        allAbsence = allAbsence + 4;
      default:
        allAbsence = allAbsence + 7;
    }
    calculatedAbsence = (int) Math.round(allAbsence * multiplier);
    return calculatedAbsence;
  }

  public int availableSickLeave(@NotNull User user) {
    int allSickLeave = 15;
    int multiplier;
    if (user.getDateOfEntry().getYear() == LocalDate.now().getYear()) {
      int restDays = 365 - user.getDateOfEntry().getDayOfYear();
      multiplier = restDays / 365;
    } else {
      multiplier = 1;
    }
    int calculatedAbsence = (int) Math.round(allSickLeave * multiplier);
    return calculatedAbsence;
  }
}