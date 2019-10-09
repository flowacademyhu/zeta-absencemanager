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
@RequestMapping("/admin/absence")
public class AdminAbsencesController {

    //@Autowired
    //private AbsenceService absenceService;

    @GetMapping("/{absenceId}")
    public Absence getOne(@PathVariable("absenceId") Integer absenceId) {
        //return absenceService.getUserById(absenceId);
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
    public Absence createAbsence(@RequestBody Absence absence) {
        //return absenceService.create();
        return absence;
    }

    @PutMapping("/{absenceId}")
    public Absence update(@PathVariable("absenceId") Integer absenceId, @RequestBody Absence absence) {
        //return absenceService.update(userId, absence);
        return absence;
    }

    @DeleteMapping("/{absenceId}")
    public void delete(@PathVariable("absenceId") Integer absenceId) {
        //return absenceService.delete(absenceId);
    }
}
