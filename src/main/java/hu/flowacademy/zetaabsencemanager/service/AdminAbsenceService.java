package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Transactional
public class AdminAbsenceService {

    @Autowired
    private AbsenceRepository absenceRepository;

    public List<Absence> findAllAbsence() {
        return this.absenceRepository.findAll();
    }

    public Absence findOne(@NotNull Long id) {
        return absenceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid."));
    }

    public Absence create(Absence absence) {
        if (absence.getType() == null || absence.getBegin() == null || absence.getEnd() == null || absence.getReporter() == null ||
                absence.getAssignee() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid.");
        }
        return absenceRepository.save(absence);
    }

    public Absence update(@NotNull Long id, @NotNull Absence absence) {
        Absence modifyAbsence = absenceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid."));
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