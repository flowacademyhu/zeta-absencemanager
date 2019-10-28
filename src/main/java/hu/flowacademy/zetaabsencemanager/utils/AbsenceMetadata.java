package hu.flowacademy.zetaabsencemanager.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AbsenceMetadata {

  private Long totalElements;
  private Integer totalPages;
  private Integer pageNumber;
  private Integer pageSize;
}
