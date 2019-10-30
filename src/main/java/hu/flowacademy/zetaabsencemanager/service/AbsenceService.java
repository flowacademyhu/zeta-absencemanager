package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.Status;
import hu.flowacademy.zetaabsencemanager.model.Type;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.model.validator.AbsenceValidator;
import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import hu.flowacademy.zetaabsencemanager.utils.AbsenceDTO;
import hu.flowacademy.zetaabsencemanager.utils.AbsenceMetadata;
import hu.flowacademy.zetaabsencemanager.utils.Constants;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

  @Autowired
  private FilterService filterService;

  public Absence findOne(@NotNull Long id) {
    Absence absence = absenceRepository.findByIdAndDeletedAtNull(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ABSENCE_NOT_FOUND));
    if (!absence.getReporter().getId().equals(authenticationService.getCurrentUser().getId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ABSENCE_NOT_FOUND);
    }
    return absence;
  }

  public AbsenceDTO findAll(Long administrationID, Type type,
      Status status, LocalDate start, LocalDate finish,
      Integer dayStart, Integer dayEnd, Pageable pageable) {
    Page<Absence> absencePage = this.absenceRepository
        .findAll(
            getFilteredAbsences(administrationID, type, status, start, finish, dayStart, dayEnd),
            pageable);

    return AbsenceDTO.builder()
        .embedded(absencePage.getContent())
        .metadata(AbsenceMetadata.builder()
            .totalElements(absencePage.getTotalElements())
            .totalPages(absencePage.getTotalPages())
            .pageNumber(absencePage.getNumber())
            .pageSize(absencePage.getSize())
            .build())
        .build();
  }

  public void increaseUsedDays(Absence absence) {
    User user = absence.getReporter();
    if (absence.getType() == Type.ABSENCE) {
      user.setUsedAbsenceDays(user.getTotalAbsenceDays() + absence.getDuration());
    } else if (absence.getType() == Type.NON_WORKING) {
      if ((user.getUsedSickLeaveDays() + absence.getDuration()) > 15) {
        user.setUsedAbsenceDays(15);
        user.setUsedSickPay(user.getUsedSickLeaveDays() + absence.getDuration() - 15);
      } else {
        user.setUsedSickLeaveDays(user.getUsedSickLeaveDays() + absence.getDuration());
      }
    } else if (absence.getType() == Type.CHILD_SICK_PAY) {
      user.setUsedChildSickPay(user.getUsedChildSickPay() + absence.getDuration());
    } else if(absence.getType()==Type.UNPAID_HOLIDAY) {
      user.setUsedNonPayAbsence(user.getUsedNonPayAbsence()+absence.getDuration());
    }
  }

  public void reduceUsedDays(Absence absence) {
    User user = absence.getReporter();
    if (absence.getType() == Type.ABSENCE) {
      user.setUsedAbsenceDays(user.getTotalAbsenceDays() - absence.getDuration());
    } else if (absence.getType() == Type.NON_WORKING) {
      if ((user.getUsedSickPay() < absence.getDuration())) {
        Integer duration = absence.getDuration() - user.getUsedSickPay();
        user.setUsedSickLeaveDays(15 - duration);
      } else {
        user.setUsedSickLeaveDays(user.getUsedSickLeaveDays() - absence.getDuration());
      }
    } else if (absence.getType() == Type.CHILD_SICK_PAY) {
      user.setUsedChildSickPay(user.getUsedChildSickPay() - absence.getDuration());
    } else if(absence.getType()==Type.UNPAID_HOLIDAY) {
      user.setUsedNonPayAbsence(user.getUsedNonPayAbsence()-absence.getDuration());
    }
  }

  public Absence create(@NotNull Absence absence) {
    this.absenceValidator.validateAbsenceSave(absence);
    absence.setReporter(authenticationService.getCurrentUser());
    absence.setAssignee(authenticationService.getCurrentUser().getGroup().getLeader());
    absence.setCreatedAt(LocalDateTime.now());
    absence.setCreatedBy(authenticationService.getCurrentUser());
    absence.setStatus(Status.OPEN);
    increaseUsedDays(absence);
    return absenceRepository.save(absence);
  }

  public Absence update(@NotNull Long id, @NotNull Absence absence) {
    Absence modifyAbsence = absenceRepository.findByIdAndDeletedAtNull(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            Constants.INVALID_ARGUMENTS));
    if (!absence.getReporter().getId().equals(authenticationService.getCurrentUser().getId())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
          Constants.UNAUTHORIZED_ABSENCE);
    }
    if (absence.getDuration() != modifyAbsence.getDuration() || !absence.getType()
        .equals(modifyAbsence.getType())) {
      increaseUsedDays(absence);
      reduceUsedDays(modifyAbsence);
    }
    modifyAbsence.setType(absence.getType());
    modifyAbsence.setBegin(absence.getBegin());
    modifyAbsence.setEnd(absence.getEnd());
    modifyAbsence.setDuration(absence.getDuration());
    modifyAbsence.setSummary(absence.getSummary());
    modifyAbsence.setReporter(absence.getReporter());
    modifyAbsence.setAssignee(absence.getAssignee());
    publisher.publishEvent(new StateChangedEvent<>(
        absence.getId(), absence.getState()));
    modifyAbsence.setUpdatedAt(LocalDateTime.now());
    modifyAbsence.setUpdatedBy(authenticationService.getCurrentUser());
    absenceRepository.save(modifyAbsence);
    return modifyAbsence;
  }

  public void delete(@NotNull Long id) {
    Absence deleted = findOne(id);
    reduceUsedDays(deleted);
    deleted.setDeletedAt(LocalDateTime.now());
    deleted.setDeletedBy(authenticationService.getCurrentUser());
    update(id, deleted);
  }

  private Specification<Absence> getFilteredAbsences(Long administrationID, Type type,
      Status status, LocalDate start, LocalDate finish,
      Integer dayStart, Integer dayEnd) {
    return Specification
        .where(filterService.filterByAdministrationID(administrationID))
        .and(filterService.filterByType(type))
        .and(filterService.filterByStatus(status))
        .and(filterService.filterByBeginStart(start))
        .and(filterService.filterByBeginFinish(finish))
        .and(filterService.filterByDaysStart(dayStart))
        .and(filterService.filterByDaysEnd(dayEnd))
        .and(filterService.filterByReporter(authenticationService.getCurrentUser()))
        .and(filterService.filterByDeletedAt());
  }

  public int availableAbsence(@NotNull User user) {
    int allAbsence = 20;
    int[] borders = {25, 28, 31, 33, 35, 37, 39, 41, 43, 45};
    double multiplier;
    int age = LocalDate.now().getYear() - user.getDateOfBirth().getYear();
    if (user.getDateOfEntry().getYear() == LocalDate.now().getYear()) {
      Integer restDays = 365 - user.getDateOfEntry().getDayOfYear();
      multiplier = restDays / 365.0;
    } else {
      multiplier = 1;
    }
    for (int i = 0; i < borders.length; i++) {
      if (age >= borders[i]) {
        allAbsence = allAbsence + 1;
      }
    }
    switch (user.getNumberOfChildren()) {
      case 0:
        break;
      case 1:
        allAbsence = allAbsence + 2;
        break;
      case 2:
        allAbsence = allAbsence + 4;
        break;
      default:
        allAbsence = allAbsence + 7;
    }
    return (int) Math.round(allAbsence * multiplier);
  }

  public int availableSickLeave(@NotNull User user) {
    int allSickLeave = 15;
    double multiplier;
    if (user.getDateOfEntry().getYear() == LocalDate.now().getYear()) {
      int restDays = 365 - user.getDateOfEntry().getDayOfYear();
      multiplier = restDays / 365.0;
    } else {
      multiplier = 1;
    }
    return (int) Math.round(allSickLeave * multiplier);
  }
}