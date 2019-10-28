package hu.flowacademy.zetaabsencemanager.repository;

import hu.flowacademy.zetaabsencemanager.model.Group;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

  Optional<Group> findByIdAndDeletedAtIsNull(Long id);

  List<Group> findAllByDeletedAtIsNull();

  // Test:
  List<Group> findAll();

  List<Group> findAllByParentId(Long id);
}
