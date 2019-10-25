package hu.flowacademy.zetaabsencemanager.controllers;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.service.AbsenceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
