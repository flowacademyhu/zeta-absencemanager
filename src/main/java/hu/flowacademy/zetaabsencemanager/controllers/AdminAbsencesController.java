package hu.flowacademy.zetaabsencemanager.controllers;


import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.Status;
import hu.flowacademy.zetaabsencemanager.model.Type;
import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import hu.flowacademy.zetaabsencemanager.service.AdminAbsenceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/absence")
public class AdminAbsencesController {

  @Autowired
  private AdminAbsenceService absenceService;

  @Autowired
  private AbsenceRepository absenceRepository;

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

  @GetMapping("/filter")
  public List<Absence> getFilteringAbsences(
      @RequestParam(required = false) Long administrationID,
      @RequestParam(required = false) Type type,
      @RequestParam(required = false) Status status) {
    Specification<Absence> spec = absenceService
        .getFilteredAbsences(administrationID, type, status);

    return absenceRepository
        .findAll(spec);
  }
}
