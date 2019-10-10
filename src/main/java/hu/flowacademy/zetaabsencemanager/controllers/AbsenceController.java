package hu.flowacademy.zetaabsencemanager.controllers;

import hu.flowacademy.zetaabsencemanager.model.*;
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
       Absence absence= Absence.builder().build();
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
