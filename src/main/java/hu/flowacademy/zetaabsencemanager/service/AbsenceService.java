package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.Status;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AbsenceService {

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private UserService userService;

    public Absence findOne(@NotNull Long id) {
        Absence absence = absenceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Absence not found"));
        if (!absence.getReporter().getId().equals(userService.getCurrentUser().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Absence not found");
        }
        return absence;
    }

    public List<Absence> findAll() {
        User current = userService.getCurrentUser();
        return absenceRepository.findByReporterAndDeletedAtNull(current);
    }

    public Absence create(@NotNull Absence absence) {
        if (absence.getType() == null ||
                absence.getBegin() == null ||
                absence.getEnd() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid.");
        }
        absence.setReporter(userService.getCurrentUser());
        absence.setCreatedAt(LocalDateTime.now());
        absence.setCreatedBy(userService.getCurrentUser());
        // TODO absence.setAssignee();
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
        modifyAbsence.setReporter(absence.getReporter());
        modifyAbsence.setAssignee(absence.getAssignee());
        modifyAbsence.setStatus(absence.getStatus());
        modifyAbsence.setUpdatedAt(LocalDateTime.now());
        // TODO modifyAbsence.setUpdatedBy(userService.getCurrentUser())
        absenceRepository.save(modifyAbsence);
        return modifyAbsence;
    }

    public void delete(@NotNull Long id) {
        Absence deleted = findOne(id);
        deleted.setDeletedAt(LocalDateTime.now());
        // TODO modifyAbsence.setDeletedBy(userService.getCurrentUser())
        update(id, deleted);
    }
}
