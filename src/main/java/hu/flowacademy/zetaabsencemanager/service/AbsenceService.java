package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.Status;
import hu.flowacademy.zetaabsencemanager.model.Type;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AbsenceService {

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

    public Absence findOne(@NotNull Long id) {
        Absence absence = absenceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Absence not found"));
        if (absence.getReporter().getId() != getCurrentUser().getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Absence not found");
        }
        return absence;
    }

    public List<Absence> findAll() {
        return getCurrentUser().getAbsences();
    }

    public Absence create(@NotNull Absence absence) {
        if (absence.getType() == null ||
                absence.getBegin() == null ||
                absence.getEnd() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid.");
        }
        absence.setReporter(getCurrentUser());
        absence.setCreatedAt(LocalDateTime.now());
        absence.setCreatedBy(getCurrentUser());
        // TODO absence.setAssignee();
        absence.setStatus(Status.OPEN);
        return absenceRepository.save(absence);
    }

    public Absence update(@NotNull Long id, @NotNull Absence absence) {
        Absence modifyAbsence = absenceRepository.findByIdAndDeletedAtNull(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid."));
        if (absence.getReporter().getId() != getCurrentUser().getId()
                || absence.getCreatedAt() == null
                || absence.getType() == null
                || absence.getBegin() == null
                || absence.getEnd() == null
                || absence.getCreatedBy() == null
                || absence.getReporter() == null
                || absence.getAssignee() == null
                || absence.getStatus() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Absence not found or the submitted arguments are invalid.");
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
