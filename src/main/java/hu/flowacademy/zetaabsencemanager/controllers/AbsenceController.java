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
        User user=User.builder()
                .firstName("Tamás")
                .lastName("Juhász")
                .dateOfBirth(LocalDate.of(1985, Month.MAY, 5))
                .role(Roles.EMPLOYEE)
                .dateOfEntry(LocalDate.of(2019, Month.JANUARY, 6))
                .dateOfEndTrial(LocalDate.of(2019, Month.APRIL, 6))
                .email("tamas.juhasz@test.com")
                .password("test")
                .numberOfChildren(0)
                .isOnTrial(false)
                .otherAbsenceEnt("none")
                .build();
        Absence absence= Absence.builder()
                .assignee(user)
                .begin(LocalDate.of(2019, Month.DECEMBER, 12))
                .end(LocalDate.of(2019, Month.DECEMBER, 25))
                .createdAt(LocalDateTime.now())
                .status(Status.OPEN)
                .type(Type.ABSENCE)
                .reporter(user)
                .summary(8)
                .build();
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
