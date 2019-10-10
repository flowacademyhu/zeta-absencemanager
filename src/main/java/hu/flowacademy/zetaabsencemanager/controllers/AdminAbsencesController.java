package hu.flowacademy.zetaabsencemanager.controllers;


import hu.flowacademy.zetaabsencemanager.model.*;
import hu.flowacademy.zetaabsencemanager.service.AdminAbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;*/
import java.util.List;

@RestController
@RequestMapping("/admin/absence")
public class AdminAbsencesController {

    @Autowired
    private AdminAbsenceService absenceService;

    @GetMapping("/{id}")
    public Absence getOne(@PathVariable("id") Long id) {

        return absenceService.findOne(id);
        //Absence absence=Absence.builder().build();
        //return absence;
    }

    @GetMapping("")
    public List<Absence> getAll() {
        return absenceService.findAllAbsence();
        //return new ArrayList<>();
    }

    @PostMapping("")
    public Absence createAbsence(@RequestBody Absence absence) {
        return absenceService.create(absence);
        //return absence;
    }

    @PutMapping("/{id}")
    public Absence update(@PathVariable("id") Long id, @RequestBody Absence absence) {
        return absenceService.update(id, absence);
        //return absence;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
      absenceService.delete(id);
    }
}
