package hu.flowacademy.zetaabsencemanager.service.filter;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class FilterByDaysEnd implements Specification<Absence> {

  private Integer end;

  public FilterByDaysEnd(Integer end) {
    this.end = end;
  }

  @Override
  public Predicate toPredicate(Root<Absence> root, CriteriaQuery<?> criteriaQuery,
      CriteriaBuilder criteriaBuilder) {
    if (end == null) {
      return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }
    return criteriaBuilder.lessThanOrEqualTo(root.get("duration"), this.end);
  }

}
