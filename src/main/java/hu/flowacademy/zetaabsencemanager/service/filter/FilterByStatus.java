package hu.flowacademy.zetaabsencemanager.service.filter;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.Status;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class FilterByStatus implements Specification<Absence> {

  private Status status;

  public FilterByStatus(Status status) {
    this.status = status;
  }

  @Override
  public Predicate toPredicate(Root<Absence> root, CriteriaQuery<?> criteriaQuery,
      CriteriaBuilder criteriaBuilder) {
    if (status == null) {
      return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }
    return criteriaBuilder.equal(root.get("status"), this.status);
  }


}
