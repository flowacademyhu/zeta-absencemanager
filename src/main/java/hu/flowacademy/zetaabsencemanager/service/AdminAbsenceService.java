package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.Status;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AdminAbsenceService {

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private UserService userService;

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "user not found");
        }
        String email = auth.getName();
        return userService.findByEmail(email);
    }

    public List<Absence> findAllAbsence() {
        return this.absenceRepository.findAll();
    }

    public Absence findOne(@NotNull Long id) {
        return absenceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid."));
    }

    public Absence create(Absence absence) {
        if (absence.getType() == null || absence.getBegin() == null || absence.getEnd() == null || absence.getReporter() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid.");
        }
        absence.setCreatedAt(LocalDateTime.now());
        absence.setCreatedBy(getCurrentUser());
        // TODO absence.setAssignee();
        absence.setStatus(Status.OPEN);
        return absenceRepository.save(absence);
    }

    public Absence update(@NotNull Long id, @NotNull Absence absence) {
        Absence modifyAbsence = absenceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid."));
        if (absence.getCreatedAt() == null
                || absence.getType() == null
                || absence.getBegin() == null
                || absence.getEnd() == null
                || absence.getCreatedBy() == null
                || absence.getReporter() == null
                || absence.getAssignee() == null
                || absence.getStatus() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid.");
        }
        modifyAbsence.setType(absence.getType());
        modifyAbsence.setBegin(absence.getBegin());
        modifyAbsence.setEnd(absence.getEnd());
        modifyAbsence.setReporter(absence.getReporter());
        modifyAbsence.setAssignee(absence.getAssignee());
        modifyAbsence.setStatus(absence.getStatus());
        modifyAbsence.setUpdatedAt(LocalDateTime.now());
        // TODO modifyAbsence.setUpdatedBy(getCurrentUser())
        absenceRepository.save(modifyAbsence);
        return modifyAbsence;
    }

    public void delete(@NotNull Long id) {
        Absence deleted = findOne(id);
        deleted.setDeletedAt(LocalDateTime.now());
        // TODO modifyAbsence.setDeletedBy(getCurrentUser())
        update(id, deleted);
    }

}