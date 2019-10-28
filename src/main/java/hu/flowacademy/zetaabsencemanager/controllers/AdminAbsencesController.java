package hu.flowacademy.zetaabsencemanager.controllers;


import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.Status;
import hu.flowacademy.zetaabsencemanager.model.Type;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import hu.flowacademy.zetaabsencemanager.service.AdminAbsenceService;
import hu.flowacademy.zetaabsencemanager.utils.AbsenceDTO;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
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
  private AdminAbsenceService adminAbsenceService;

  @Autowired
  private AbsenceRepository absenceRepository;

  @GetMapping("/{id}")
  public Absence getOne(@PathVariable("id") Long id) {
    return adminAbsenceService.findOne(id);
  }

  @GetMapping("")
  public AbsenceDTO getAll(Pageable pageable,
      @RequestParam(required = false) Long administrationID,
      @RequestParam(required = false) Type type,
      @RequestParam(required = false) Status status,
      @RequestParam(required = false) User reporter,
      @RequestParam(required = false) User assignee,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finish,
      @RequestParam(required = false) Integer dayStart,
      @RequestParam(required = false) Integer dayEnd
  ) {
    Specification<Absence> spec = adminAbsenceService
        .getFilteredAbsences(administrationID, type, status, reporter, assignee, start, finish,
            dayStart, dayEnd);
    return adminAbsenceService.findAllAbsence(spec, pageable);
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
