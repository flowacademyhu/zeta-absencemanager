package hu.flowacademy.zetaabsencemanager.service.filter;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.Type;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class FilterByType implements Specification<Absence> {

  private Type type;

  public FilterByType(Type type) {
    this.type = type;
  }

  @Override
  public Predicate toPredicate(Root<Absence> root, CriteriaQuery<?> criteriaQuery,
      CriteriaBuilder criteriaBuilder) {
    if (type == null) {
      return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }
    return criteriaBuilder.equal(root.get("type"), this.type);
  }

}
