package hu.flowacademy.zetaabsencemanager.repository;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {

  Optional<Absence> findByIdAndDeletedAtNull(Long id);

  List<Absence> findByReporterAndDeletedAtNull(User user);

  List<Absence> findByAssigneeAndDeletedAtNull(User user);
}
