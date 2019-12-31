package hu.flowacademy.zetaabsencemanager.utils;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageableDTO<T> {

  private PageableMetadata metadata;
  private List<T> embedded;
}
