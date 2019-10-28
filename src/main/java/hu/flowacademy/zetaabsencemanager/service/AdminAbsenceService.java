package hu.flowacademy.zetaabsencemanager.service;


import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.Status;
import hu.flowacademy.zetaabsencemanager.model.Type;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.model.validator.AbsenceValidator;
import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import hu.flowacademy.zetaabsencemanager.repository.GroupRepository;
import hu.flowacademy.zetaabsencemanager.utils.AbsenceDTO;
import hu.flowacademy.zetaabsencemanager.utils.AbsenceMetadata;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
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
  private AuthenticationService authenticationService;

  @Autowired
  private AbsenceValidator absenceValidator;

  @Autowired
  private GroupRepository groupRepository;

  @Autowired
  private ApplicationEventPublisher publisher;

  @Autowired
  private FilterService filterService;

  public AbsenceDTO findAllAbsence(Specification<Absence> spec, Pageable pageable) {
    if (this.authenticationService.hasRole(Roles.ADMIN)) {
      Page<Absence> absencePage = this.absenceRepository.findAll(spec, pageable);
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
          .findByAssigneeAndDeletedAtNull(this.authenticationService.getCurrentUser(), spec,
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
  }

  public Absence findOne(@NotNull Long id) {
    User current = authenticationService.getCurrentUser();
    Absence foundAbsence = absenceRepository.findById(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "The submitted arguments are invalid."));
    if (this.authenticationService.hasRole(Roles.ADMIN) || (
        foundAbsence.getAssignee().equals(current))) {
      return foundAbsence;
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Can not access data");
    }
  }

  public Absence create(Absence absence) {
    this.absenceValidator.validateAbsenceSave(absence);
    absence.setCreatedAt(LocalDateTime.now());
    absence.setCreatedBy(authenticationService.getCurrentUser());
    absence.setAssignee(absence.getAssignee());
    absence.setStatus(Status.OPEN);
    if (this.authenticationService.hasRole(Roles.ADMIN)) {
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
    if (this.authenticationService.hasRole(Roles.ADMIN) || (
        modifyAbsence.getAssignee().equals(current))) {
      modifyAbsence.setType(absence.getType());
      modifyAbsence.setBegin(absence.getBegin());
      modifyAbsence.setSummary(absence.getSummary());
      modifyAbsence.setDuration(absence.getDuration());
      modifyAbsence.setEnd(absence.getEnd());
      modifyAbsence.setReporter(absence.getReporter());
      modifyAbsence.setAssignee(absence.getAssignee());
      publisher.publishEvent(new StateChangedEvent<>(
          absence.getId(), absence.getState()));
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

  public Specification<Absence> getFilteredAbsences(Long administrationID, Type type,
      Status status, User reporter, User assignee, LocalDate start, LocalDate end,
      Integer dayStart, Integer dayEnd) {
    Specification<Absence> spec = Specifications
        .where(filterService.filterByAdministrationID(administrationID))
        .and(filterService.filterByType(type))
        .and(filterService.filterByStatus(status))
        .and(filterService.filterByReporter(reporter))
        .and(filterService.filterByAssignee(assignee))
        .and(filterService.filterByBeginStart(start))
        .and(filterService.filterByBeginEnd(end))
        .and(filterService.filterByDaysStart(dayStart))
        .and(filterService.filterByDaysEnd(dayEnd));
    return spec;
  }

}