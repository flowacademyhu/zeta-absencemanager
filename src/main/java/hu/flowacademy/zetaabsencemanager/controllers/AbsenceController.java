package hu.flowacademy.zetaabsencemanager.controllers;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.service.AbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/absence")
public class AbsenceController {

    @Autowired
    private AbsenceService absenceService;

    @GetMapping("/{id}")
    public Absence getOne(@PathVariable("id") Long id) {
        return absenceService.findOne(id);
    }

    @GetMapping("")
    public List<Absence> getAll() {
        return absenceService.findAll();
    }

    @PostMapping("")
    public Absence create(@RequestBody Absence absence) {
        return absenceService.create(absence);
    }

    @PutMapping("/{id}")
    public Absence update(@PathVariable("id") Long id, @RequestBody Absence absence) {
        return absenceService.update(id, absence);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") Long userId) {
        // absenceService.delete(userId);
    }

}
