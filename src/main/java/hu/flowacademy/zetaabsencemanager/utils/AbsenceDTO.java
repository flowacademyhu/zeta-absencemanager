package hu.flowacademy.zetaabsencemanager.utils;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AbsenceDTO {

  private AbsenceMetadata metadata;
  private List<Absence> embedded;
}
