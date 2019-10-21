package hu.flowacademy.zetaabsencemanager.service;


import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.Status;
import hu.flowacademy.zetaabsencemanager.model.Group;
import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import hu.flowacademy.zetaabsencemanager.repository.GroupRepository;
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
import java.util.*;

@Service
@Transactional
public class AdminAbsenceService {

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private GroupRepository groupRepository;


    public Set<User> getEmployees(Group g, Set<User> employees) {
        employees.addAll(g.getLeaders());
        employees.addAll(g.getEmployees());
        List<Group> children = groupRepository.findAllByParentId(g.getId());
        ListIterator<Group> iterator = children.listIterator();
        while (iterator.hasNext()) {
            return getEmployees(iterator.next(), employees);
        }
        return employees;
    }

    public List<Absence> findAllAbsence() {
        User current = authenticationService.getCurrentUser();
        if (current.getRole() == Roles.ADMIN) {
            return this.absenceRepository.findAll();
        } else {
            Set<User> employees = getEmployees(current.getGroup(), new HashSet<>());
            List<Absence> absences = new ArrayList<>();
            for (User emp : employees) {
                absences.addAll(emp.getAbsences());
            }
            return absences;
        }
    }

    public Absence findOne(@NotNull Long id) {
        User current = authenticationService.getCurrentUser();
        Absence foundAbsence = absenceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid."));
        Set<User> employees = getEmployees(current.getGroup(), new HashSet<>());
        if (current.getRole() == Roles.ADMIN || (current.getRole() == Roles.LEADER && employees.contains(foundAbsence.getReporter()))) {
            return foundAbsence;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Can not access data");
        }
    }

    public Absence create(Absence absence) {
        User current = authenticationService.getCurrentUser();
        if (absence.getType() == null || absence.getBegin() == null || absence.getEnd() == null || absence.getReporter() == null ||
                absence.getAssignee() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid.");
        }
        absence.setCreatedAt(LocalDateTime.now());
        absence.setCreatedBy(authenticationService.getCurrentUser());
        // TODO absence.setAssignee();
        absence.setStatus(Status.OPEN);
        Set<User> employees = getEmployees(current.getGroup(), new HashSet<>());
        if (current.getRole() == Roles.ADMIN || (current.getRole() == Roles.LEADER && employees.contains(absence.getReporter()))) {
            return absenceRepository.save(absence);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Can not access data");
        }
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
        User current = authenticationService.getCurrentUser();
        Set<User> employees = getEmployees(current.getGroup(), new HashSet<>());
        if (current.getRole() == Roles.ADMIN || (current.getRole() == Roles.LEADER && employees.contains(modifyAbsence.getReporter()))) {
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
            else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Can not access data");
            }
        }

    public void delete(@NotNull Long id) {
        Absence deleted = findOne(id);
        deleted.setDeletedAt(LocalDateTime.now());
        // TODO modifyAbsence.setDeletedBy(userService.getCurrentUser())
        update(id, deleted);
    }

}