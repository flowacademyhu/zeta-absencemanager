package hu.flowacademy.zetaabsencemanager.service.filter;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.User;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class FilterByAssignee implements Specification<Absence> {

  private User assignee;

  public FilterByAssignee(User assignee) {
    this.assignee = assignee;
  }

  @Override
  public Predicate toPredicate(Root<Absence> root, CriteriaQuery<?> criteriaQuery,
      CriteriaBuilder criteriaBuilder) {
    if (assignee == null) {
      return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }
    return criteriaBuilder.equal(root.get("assignee"), this.assignee);
  }
}
