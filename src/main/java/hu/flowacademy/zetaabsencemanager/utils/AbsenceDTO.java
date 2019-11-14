package hu.flowacademy.zetaabsencemanager.utils;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AbsenceDTO<T> {

  private AbsenceMetadata metadata;
  private List<T> embedded;
}
