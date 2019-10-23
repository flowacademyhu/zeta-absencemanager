package hu.flowacademy.zetaabsencemanager.model.validator;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AbsenceValidator {

  public void validateAbsenceSave(Absence absence) {
    if (absence.getBegin().isAfter(absence.getEnd())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Begin date can't be after end date.");
    }
  }
}
