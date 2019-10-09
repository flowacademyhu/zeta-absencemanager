package hu.flowacademy.zetaabsencemanager.repository;

import hu.flowacademy.zetaabsencemanager.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
