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
        if (!absence.getReporter().getId().equals(getCurrentUser().getId())) {
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
        absence.setReporter(userService.findOneUser(getCurrentUser().getId()));
        // TODO absence.setAssignee();
        absence.setStatus(Status.OPEN);
        return absenceRepository.save(absence);
    }

    public Absence update(@NotNull Long id, @NotNull Absence absence) {
        Absence modifyAbsence = absenceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid."));
        if (!absence.getReporter().getId().equals(getCurrentUser().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Absence not found");
        }
        modifyAbsence.setAssignee(absence.getAssignee());
        modifyAbsence.setBegin(absence.getBegin());
        modifyAbsence.setEnd(absence.getEnd());
        modifyAbsence.setReporter(absence.getReporter());
        modifyAbsence.setStatus(absence.getStatus());
        modifyAbsence.setType(absence.getType());
        absenceRepository.save(modifyAbsence);
        return modifyAbsence;
    }

}
