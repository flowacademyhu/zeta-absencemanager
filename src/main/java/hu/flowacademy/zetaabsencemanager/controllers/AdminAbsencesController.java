package hu.flowacademy.zetaabsencemanager.controllers;


import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.service.AdminAbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/absence")
public class AdminAbsencesController {

    @Autowired
    private AdminAbsenceService absenceService;

    @GetMapping("/{id}")
    public Absence getOne(@PathVariable("id") Long id) {
        return absenceService.findOne(id);
    }

    @GetMapping("")
    public List<Absence> getAll() {
        return absenceService.findAllAbsence();
    }

    @PostMapping("")
    public Absence createAbsence(@RequestBody Absence absence) {
        return absenceService.create(absence);
    }

    @PutMapping("/{id}")
    public Absence update(@PathVariable("id") Long id, @RequestBody Absence absence) {
        return absenceService.update(id, absence);
    }
}
