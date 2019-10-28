package hu.flowacademy.zetaabsencemanager.service.filter;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class FilterByAdministrationID implements Specification<Absence> {

  private Long administrationID;

  public FilterByAdministrationID(Long administrationID) {
    this.administrationID = administrationID;
  }

  @Override
  public Predicate toPredicate(Root<Absence> root, CriteriaQuery<?> criteriaQuery,
      CriteriaBuilder criteriaBuilder) {
    if (administrationID == null) {
      return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }
    return criteriaBuilder.equal(root.get("administrationID"), this.administrationID);
  }
}

