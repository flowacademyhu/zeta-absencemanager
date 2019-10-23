package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.Status;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.model.validator.AbsenceValidator;
import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class AbsenceService {

  @Autowired
  private AbsenceRepository absenceRepository;

  @Autowired
  private AuthenticationService authenticationService;

<<<<<<< HEAD
    public Absence findOne(@NotNull Long id) {
        Absence absence = absenceRepository.findByIdAndDeletedAtNull(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Absence not found"));
        if (!absence.getReporter().getId().equals(userService.getCurrentUser().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Absence not found");
        }
        return absence;
    }
=======
  @Autowired
  private AbsenceValidator absenceValidator;
>>>>>>> development

  public Absence findOne(@NotNull Long id) {
    Absence absence = absenceRepository.findByIdAndDeletedAtNull(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Absence not found"));
    if (!absence.getReporter().getId().equals(authenticationService.getCurrentUser().getId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Absence not found");
    }
    return absence;
  }

<<<<<<< HEAD
    public Absence create(@NotNull Absence absence) {
        if (absence.getType() == null ||
                absence.getBegin() == null ||
                absence.getEnd() == null ||
                absence.getBegin().isAfter(absence.getEnd())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid.");
        }
        absence.setReporter(userService.getCurrentUser());
        absence.setCreatedAt(LocalDateTime.now());
        absence.setCreatedBy(userService.getCurrentUser());
       /* if(absence.getReporter().getGroup().getLeader()!=null){
            absence.setAssignee(absence.getReporter().getGroup().getLeader());
        }*/
        //absence.setDuration();
        absence.setStatus(Status.OPEN);
        /*userService.getCurrentUser().getAbsences().add(absence);
        userService.updateUser(userService.getCurrentUser().getId(), userService.getCurrentUser());*/
        return absenceRepository.save(absence);
    }

    public Absence update(@NotNull Long id, @NotNull Absence absence) {
        Absence modifyAbsence = absenceRepository.findByIdAndDeletedAtNull(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid."));
        if (!absence.getReporter().getId().equals(userService.getCurrentUser().getId())
                //|| absence.getCreatedAt() == null
                || absence.getType() == null
                || absence.getBegin() == null
                || absence.getEnd() == null)
        //|| absence.getCreatedBy() == null
        //|| absence.getReporter() == null
        //|| absence.getAssignee() == null
        //|| absence.getStatus() == null) {
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Absence not found or the submitted arguments are invalid.");
        }
        modifyAbsence.setType(absence.getType());
        modifyAbsence.setBegin(absence.getBegin());
        modifyAbsence.setEnd(absence.getEnd());
        if (!StringUtils.isEmpty(absence.getSummary())) {
            modifyAbsence.setSummary(absence.getSummary());
        }
        modifyAbsence.setReporter(absence.getReporter());
        modifyAbsence.setAssignee(absence.getAssignee());
        modifyAbsence.setStatus(absence.getStatus());
        modifyAbsence.setUpdatedAt(LocalDateTime.now());
        // TODO modifyAbsence.setUpdatedBy(userService.getCurrentUser())
        absenceRepository.save(modifyAbsence);
        return modifyAbsence;
    }
=======
  public List<Absence> findAll() {
    User current = authenticationService.getCurrentUser();
    return absenceRepository.findByReporterAndDeletedAtNull(current);
  }

  public Absence create(@NotNull Absence absence) {
    this.absenceValidator.validateAbsenceSave(absence);
    absence.setReporter(authenticationService.getCurrentUser());
    absence.setCreatedAt(LocalDateTime.now());
    absence.setCreatedBy(authenticationService.getCurrentUser());
    absence.setStatus(Status.OPEN);
    return absenceRepository.save(absence);
  }
>>>>>>> development

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
    modifyAbsence.setSummary(absence.getSummary());
    modifyAbsence.setReporter(absence.getReporter());
    modifyAbsence.setAssignee(absence.getAssignee());
    modifyAbsence.setStatus(absence.getStatus());
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
}
