package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.Status;
import hu.flowacademy.zetaabsencemanager.model.Type;
import hu.flowacademy.zetaabsencemanager.model.validator.AbsenceValidator;
import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
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
        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Absence not found"));
    if (!absence.getReporter().getId().equals(authenticationService.getCurrentUser().getId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Absence not found");
    }
    return absence;
  }

  public AbsenceDTO findAll(Specification<Absence> spec, Pageable pageable) {
    //User current = authenticationService.getCurrentUser();
    Page<Absence> absencePage = this.absenceRepository
        .findAll(spec, pageable);

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

  public Specification<Absence> getFilteredAbsences(Long administrationID, Type type,
      Status status, LocalDate start, LocalDate finish,
      Integer dayStart, Integer dayEnd) {
    Specification<Absence> spec = Specifications
        .where(filterService.filterByAdministrationID(administrationID))
        .and(filterService.filterByType(type))
        .and(filterService.filterByStatus(status))
        .and(filterService.filterByBeginStart(start))
        .and(filterService.filterByBeginFinish(finish))
        .and(filterService.filterByDaysStart(dayStart))
        .and(filterService.filterByDaysEnd(dayEnd))
        .and(filterService.filterByReporter(authenticationService.getCurrentUser()))
        .and(filterService.filterByDeletedAt());
    return spec;
  }
}