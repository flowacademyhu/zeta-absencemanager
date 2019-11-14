package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.Group;
import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.Status;
import hu.flowacademy.zetaabsencemanager.model.Type;
import hu.flowacademy.zetaabsencemanager.model.User;
import java.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class FilterService {

  public Specification<Absence> filterByType(Type type) {
    return (root, query, cb) -> {
      if (type == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.equal(root.get("type"), type);
    };
  }

  public Specification<Absence> filterByStatus(Status status) {
    return (root, query, cb) -> {
      if (status == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.equal(root.get("status"), status);
    };
  }

  public Specification<Absence> filterByReporterFirstName(String reporter) {
    return (root, query, cb) -> {
      if (reporter == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.like(root.get("reporter").get("firstName"), "%" + reporter + "%");
    };
  }

  public Specification<Absence> filterByReporterLastName(String reporter) {
    return (root, query, cb) -> {
      if (reporter == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.like(root.get("reporter").get("lastName"), "%" + reporter + "%");
    };
  }

  public Specification<Absence> filterByReporter(User reporter) {
    return (root, query, cb) -> {
      if (reporter == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.equal(root.get("reporter"), reporter);
    };
  }


  public Specification<Absence> filterByDaysStart(Integer start) {
    return (root, query, cb) -> {
      if (start == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.greaterThanOrEqualTo(root.get("duration"), start);
    };
  }

  public Specification<Absence> filterByDaysEnd(Integer end) {
    return (root, query, cb) -> {
      if (end == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.lessThanOrEqualTo(root.get("duration"), end);
    };
  }

  public Specification<Absence> filterByBeginStart(LocalDate start) {
    return (root, query, cb) -> {
      if (start == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.greaterThanOrEqualTo(root.get("end"), start);
    };
  }

  public Specification<Absence> filterByBeginFinish(LocalDate finish) {
    return (root, query, cb) -> {
      if (finish == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.lessThanOrEqualTo(root.get("begin"), finish);
    };
  }

  public Specification<Absence> filterByAssigneeFirstName(String assignee) {
    return (root, query, cb) -> {
      if (assignee == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.like(root.get("assignee").get("firstName"), "%" + assignee + "%");
    };
  }

  public Specification<Absence> filterByAssigneeLastName(String assignee) {
    return (root, query, cb) -> {
      if (assignee == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.like(root.get("assignee").get("lastName"), "%" + assignee + "%");
    };
  }

  public Specification<Absence> filterByAssignee(User assignee) {
    return (root, query, cb) -> {
      if (assignee == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.equal(root.get("assignee"), assignee);
    };
  }

  public Specification<Absence> filterByAdministrationID(Long administrationID) {
    return (root, query, cb) -> {
      if (administrationID == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.equal(root.get("administrationID"), administrationID);
    };
  }

  public Specification<Absence> filterByDeletedAt() {
    return (root, query, cb) -> cb.isNull(root.get("deletedAt"));
  }

  public Specification<User> userFilterByFirstname(String name) {
    return (root, query, cb) -> {
      if (name == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.like(root.get("firstName"), "%" + name + "%");
    };
  }

  public Specification<User> userFilterByLastname(String name) {
    return (root, query, cb) -> {
      if (name == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.like(root.get("lastName"), "%" + name + "%");
    };
  }

  public Specification<User> userFilterByDateOfEntryStart(LocalDate dateOfEntryStart) {
    return (root, query, cb) -> {
      if (dateOfEntryStart == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.greaterThanOrEqualTo(root.get("dateOfEntry"), dateOfEntryStart);
    };
  }

  public Specification<User> userFilterByDateOfEntryFinish(LocalDate dateOfEntryFinish) {
    return (root, query, cb) -> {
      if (dateOfEntryFinish == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.lessThanOrEqualTo(root.get("dateOfEntry"), dateOfEntryFinish);
    };
  }

  public Specification<User> userFilterByDateOfEndTrialStart(LocalDate dateOfEndTrialStart) {
    return (root, query, cb) -> {
      if (dateOfEndTrialStart == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.greaterThanOrEqualTo(root.get("dateOfEndTrial"), dateOfEndTrialStart);
    };
  }

  public Specification<User> userFilterByDateOfEndTrialFinish(LocalDate dateOfEndTrialFinish) {
    return (root, query, cb) -> {
      if (dateOfEndTrialFinish == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.lessThanOrEqualTo(root.get("dateOfEndTrial"), dateOfEndTrialFinish);
    };
  }

  public Specification<User> userFilterByGroup(Group group) {
    return (root, query, cb) -> {
      if (group == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.equal(root.get("group"), group);
    };
  }

  public Specification<User> userFilterByPosition(String position) {
    return (root, query, cb) -> {
      if (position == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.like(root.get("position"), "%" + position + "%");
    };
  }

  public Specification<User> userFilterByRole(Roles role) {
    return (root, query, cb) -> {
      if (role == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.equal(root.get("role"), role);
    };
  }
}
