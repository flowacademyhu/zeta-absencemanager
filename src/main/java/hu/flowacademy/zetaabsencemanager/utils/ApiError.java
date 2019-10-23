package hu.flowacademy.zetaabsencemanager.utils;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ApiError {

  private HttpStatus status;
  private String message;
  private List<String> errors;

}
