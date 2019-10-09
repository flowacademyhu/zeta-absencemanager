package hu.flowacademy.zetaabsencemanager.controllers;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.Status;
import hu.flowacademy.zetaabsencemanager.model.Type;
import hu.flowacademy.zetaabsencemanager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

@RestController
@RequestMapping("/absence")
public class AbsenceController {

    //@Autowired
    //private AbsenceService absenceService;

    @GetMapping("/{id}")
    public Absence getOne(@PathVariable("id") Long id) {
        ///return absenceService.getAbsenceById(id);
        Absence absence=new Absence();
        absence.setAssignee(new User());
        absence.setBegin(LocalDate.of(2019, Month.DECEMBER, 12));
        absence.setEnd(LocalDate.of(2019, Month.DECEMBER, 25));
        absence.setCreatedAt(LocalDateTime.now());
        absence.setStatus(Status.OPEN);
        absence.setType(Type.ABSENCE);
        absence.setReporter(new User());
        absence.setSummary(8);
        return absence;
    }

    @GetMapping("")
    public ArrayList<Absence> getAll() {
        //return absenceService.getAll();
        return new ArrayList<>();
    }

    @PostMapping("")
    public Absence create(@RequestBody Absence absence) {
        //return absenceService.create(absence);
        return absence;
    }

    @PutMapping("/{id}")
    public Absence update(@PathVariable("id") Long id, @RequestBody Absence absence) {
        //return absenceService.update(id, absence);
        return absence;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        //return absenceService.delete(id);
    }
}
