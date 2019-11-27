package hu.flowacademy.zetaabsencemanager.service;


import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.Group;
import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.Status;
import hu.flowacademy.zetaabsencemanager.model.Type;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.model.validator.AbsenceValidator;
import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import hu.flowacademy.zetaabsencemanager.repository.GroupRepository;
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
public class AdminAbsenceService {

  @Autowired
  private AbsenceRepository absenceRepository;

  @Autowired
  private AbsenceService absenceService;

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private AbsenceValidator absenceValidator;

  @Autowired
  private GroupRepository groupRepository;

  @Autowired
  private ApplicationEventPublisher publisher;

  @Autowired
  private FilterService filterService;

  public AbsenceDTO findAllAbsence(Long administrationID, Type type,
      Status status, String reporter, String assignee, LocalDate start, LocalDate finish,
      Integer dayStart, Integer dayEnd, Pageable pageable) {
    if (this.authenticationService.hasRole(Roles.ADMIN)) {
      Page<Absence> absencePage = this.absenceRepository.findAll(
          getFilteredAbsences(administrationID, type, status, reporter, assignee, start, finish,
              dayStart, dayEnd), pageable);
      return AbsenceDTO.builder()
          .embedded(absencePage.getContent())
          .metadata(AbsenceMetadata.builder()
              .totalElements(absencePage.getTotalElements())
              .totalPages(absencePage.getTotalPages())
              .pageNumber(absencePage.getNumber())
              .pageSize(absencePage.getSize())
              .build())
          .build();
    } else {
      Page<Absence> absencePage = this.absenceRepository
          .findAll(
              getFilteredAbsencesLeader(administrationID, type, status, reporter, start, finish,
                  dayStart, dayEnd), pageable);
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
  }

  public Absence findOne(@NotNull Long id) {
    Absence foundAbsence = absenceRepository.findById(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            Constants.INVALID_ARGUMENTS));
    if (this.authenticationService.hasRole(Roles.ADMIN) || (
        foundAbsence.getAssignee().equals(authenticationService.getCurrentUser()))) {
      return foundAbsence;
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Constants.CAN_NOT_ACCESS_DATA);
    }
  }

  public Absence create(Absence absence) {
    this.absenceValidator.validateAbsenceSave(absence);
    Group group = absence.getReporter().getGroup();
    absenceService.intervallValidate(absence, group);
    absence.setCreatedAt(LocalDateTime.now());
    absence.setCreatedBy(authenticationService.getCurrentUser());
    absence.setAssignee(absence.getAssignee());
    absence.setStatus(Status.OPEN);
    if (this.authenticationService.hasRole(Roles.ADMIN)) {
      absenceService.addToUsedDays(absence);
      return absenceRepository.save(absence);
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Constants.CAN_NOT_ACCESS_DATA);
    }

  }

  public Absence update(@NotNull Long id, @NotNull Absence absence) {
    this.absenceValidator.validateAbsenceSave(absence);
    Absence modifyAbsence = absenceRepository.findById(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            Constants.INVALID_ARGUMENTS));
    User current = authenticationService.getCurrentUser();
    absenceService.intervallValidate(absence, absence.getReporter().getGroup());
    if (this.authenticationService.hasRole(Roles.ADMIN) || (
        modifyAbsence.getAssignee().equals(current))) {
      modifyAbsence.setType(absence.getType());
      modifyAbsence.setBegin(absence.getBegin());
      modifyAbsence.setSummary(absence.getSummary());
      modifyAbsence.setAdministrationID(absence.getAdministrationID());
      if (modifyAbsence.getDuration() != absence.getDuration()) {
        absenceService.addToUsedDays(absence);
        absenceService.removeFromUsedDays(modifyAbsence);
      }
      modifyAbsence.setDuration(absence.getDuration());
      modifyAbsence.setEnd(absence.getEnd());
      modifyAbsence.setReporter(absence.getReporter());
      modifyAbsence.setAssignee(absence.getAssignee());
      publisher.publishEvent(new StateChangedEvent<>(
          absence.getId(), absence.getState()));
      modifyAbsence.setUpdatedAt(LocalDateTime.now());
      modifyAbsence.setUpdatedBy(authenticationService.getCurrentUser());
      return absenceRepository.save(modifyAbsence);
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Constants.CAN_NOT_ACCESS_DATA);
    }
  }

  public void delete(@NotNull Long id) {
    Absence deleted = findOne(id);
    absenceService.removeFromUsedDays(deleted);
    deleted.setDeletedAt(LocalDateTime.now());
    deleted.setDeletedBy(authenticationService.getCurrentUser());
    update(id, deleted);
  }

  public Specification<Absence> getFilteredAbsences(Long administrationID, Type type,
      Status status, String reporter, String assignee, LocalDate start, LocalDate finish,
      Integer dayStart, Integer dayEnd) {
    return Specification
        .where(filterService.filterByType(type))
        .and(filterService.filterByAdministrationID(administrationID))
        .and(filterService.filterByStatus(status))
        .and(filterService.filterByReporterFirstName(reporter)
            .or(filterService.filterByReporterLastName(reporter)))
        .and(filterService.filterByAssigneeFirstName(assignee)
            .or(filterService.filterByAssigneeLastName(assignee)))
        .and(filterService.filterByBeginStart(start))
        .and(filterService.filterByBeginFinish(finish))
        .and(filterService.filterByDaysStart(dayStart))
        .and(filterService.filterByDaysEnd(dayEnd));
  }

  public Specification<Absence> getFilteredAbsencesLeader(Long administrationID, Type type,
      Status status, String reporter, LocalDate start, LocalDate finish,
      Integer dayStart, Integer dayEnd) {
    return Specification
        .where(filterService.filterByAdministrationID(administrationID))
        .and(filterService.filterByType(type))
        .and(filterService.filterByStatus(status))
        .and(filterService.filterByReporterFirstName(reporter)
            .or(filterService.filterByReporterLastName(reporter)))
        .and(filterService.filterByAssignee(authenticationService.getCurrentUser()))
        .and(filterService.filterByBeginStart(start))
        .and(filterService.filterByBeginFinish(finish))
        .and(filterService.filterByDaysStart(dayStart))
        .and(filterService.filterByDaysEnd(dayEnd))
        .and(filterService.filterByDeletedAt());
  }
}