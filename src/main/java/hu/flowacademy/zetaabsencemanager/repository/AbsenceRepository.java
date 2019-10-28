package hu.flowacademy.zetaabsencemanager.repository;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long>,
    JpaSpecificationExecutor<Absence> {

  Optional<Absence> findByIdAndDeletedAtNull(Long id);

  //Page<Absence> findByReporterAndDeletedAtNull(User user, Specification<Absence> spec,
  //   Pageable pageable);

  //Page<Absence> findByAssigneeAndDeletedAtNull(User user, Specification<Absence> spec,
  //    Pageable pageable);
}
