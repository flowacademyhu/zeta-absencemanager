package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.Group;
import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import hu.flowacademy.zetaabsencemanager.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminAbsenceService {

    private List<User> employees = new ArrayList();

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private AbsenceService absenceService;

    public Group getEmployees(Group g) {
        employees.addAll(g.getLeaders());
        employees.addAll(g.getEmployees());
        List<Group> children = groupRepository.findAllByParentId(g.getId());
        ListIterator<Group> iterator = children.listIterator();
        while (iterator.hasNext()) {
            return getEmployees(iterator.next());
        }
        return null;
    }

    public List<Absence> findAllAbsence() {
        User current = absenceService.getCurrentUser();
        if (current.getRole() == Roles.ADMIN) {
            return this.absenceRepository.findAll();
        } else {
            this.employees.clear();
            getEmployees(current.getGroup());
            List<Absence> absences = new ArrayList<>();
            for (User emp : employees) {
                absences.addAll(emp.getAbsences());
            }
            return absences;
        }
    }

    public Absence findOne(@NotNull Long id) {
        User current = absenceService.getCurrentUser();
        if (current.getRole() == Roles.ADMIN) {
            return absenceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid."));
        } else {
            List<Absence> absences = findAllAbsence();
            if (absences.stream().map(abs -> abs.getId()).filter(x -> x == id).collect(Collectors.toList()).size() == 0) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Can not access data");
            }
            return absenceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid."));
        }
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