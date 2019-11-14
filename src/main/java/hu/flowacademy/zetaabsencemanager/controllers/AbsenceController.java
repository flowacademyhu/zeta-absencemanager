package hu.flowacademy.zetaabsencemanager.controllers;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.Status;
import hu.flowacademy.zetaabsencemanager.model.Type;
import hu.flowacademy.zetaabsencemanager.service.AbsenceService;
import hu.flowacademy.zetaabsencemanager.utils.AbsenceDTO;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/absence")
public class AbsenceController {

  @Autowired
  private AbsenceService absenceService;

  @GetMapping("/{id}")
  public Absence getOne(@PathVariable("id") Long id) {
    return absenceService.findOne(id);
  }

  @GetMapping("")
  public AbsenceDTO<Absence> getAll(Pageable pageable,
      @RequestParam(required = false) Long administrationID,
      @RequestParam(required = false) Type type,
      @RequestParam(required = false) Status status,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finish,
      @RequestParam(required = false) Integer dayStart,
      @RequestParam(required = false) Integer dayEnd
  ) {
    return absenceService.findAll(administrationID, type, status, start, finish,
        dayStart, dayEnd, pageable);
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
