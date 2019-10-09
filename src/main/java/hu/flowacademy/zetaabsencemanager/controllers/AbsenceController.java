package hu.flowacademy.zetaabsencemanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/absence")
public class AbsenceController {

    //@Autowired
    //private AbsenceService absenceService;

    @GetMapping("/{absenceId}")
    public Absence getOne(@PathVariable("absenceId") Integer absenceId) {
        ///return absenceService.getAbsenceById(absenceId);
    }

    @GetMapping("")
    public ArrayList<Absence> getAll() {
        //return absenceService.getAll();
    }

    @PostMapping("")
    public Absence create(@RequestBody Absence absence) {
        //return absenceService.create(absence);
    }

    @PutMapping("/{absenceId}")
    public Absence update(@PathVariable("absenceId") Integer absenceId, @RequestBody Absence absence) {
        //return absenceService.update(absenceId, absence);
    }

    @DeleteMapping("{absenceId}")
    public void delete(@PathVariable("absenceId") Integer absenceId) {
        //return absenceService.delete(absenceId);
    }
}
