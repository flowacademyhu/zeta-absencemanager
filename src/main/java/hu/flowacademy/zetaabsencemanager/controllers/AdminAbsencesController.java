package hu.flowacademy.zetaabsencemanager.controllers;


import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.service.AdminAbsenceService;
import hu.flowacademy.zetaabsencemanager.utils.AbsenceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/absence")
public class AdminAbsencesController {

  @Autowired
  private AdminAbsenceService adminAbsenceService;

  @GetMapping("/{id}")
  public Absence getOne(@PathVariable("id") Long id) {
    return adminAbsenceService.findOne(id);
  }

  @GetMapping("")
  public AbsenceDTO getAll(Pageable pageable) {
    return adminAbsenceService.findAllAbsence(pageable);
  }

  @PostMapping("")
  public Absence createAbsence(@RequestBody Absence absence) {
    return adminAbsenceService.create(absence);
  }

  @PutMapping("/{id}")
  public Absence update(@PathVariable("id") Long id, @RequestBody Absence absence) {
    return adminAbsenceService.update(id, absence);
  }
}
