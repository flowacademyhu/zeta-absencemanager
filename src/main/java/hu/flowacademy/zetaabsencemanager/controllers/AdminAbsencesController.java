package hu.flowacademy.zetaabsencemanager.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/admin/absence")
public class AdminAbsencesController {

    //@Autowired
    //private AbsenceService absenceService;

    @GetMapping("/{absenceId}")
    public Absence getOne(@PathVariable("absenceId") Integer absenceId) {
        //return absenceService.getUserById(absenceId);
    }

    @GetMapping("")
    public ArrayList<Absence> getAll() {
        //return absenceService.getAll();
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
