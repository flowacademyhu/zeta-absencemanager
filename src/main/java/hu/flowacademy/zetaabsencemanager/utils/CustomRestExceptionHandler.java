package hu.flowacademy.zetaabsencemanager.utils;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CustomRestExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseBody
  public ResponseEntity<List<String>> handleConstraintViolation(
      ConstraintViolationException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getConstraintViolations().stream()
        .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage()).collect(
            Collectors.toList()));
  }
}
