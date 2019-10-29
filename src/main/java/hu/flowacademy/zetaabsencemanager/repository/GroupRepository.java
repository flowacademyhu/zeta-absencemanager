package hu.flowacademy.zetaabsencemanager.repository;

import hu.flowacademy.zetaabsencemanager.model.Group;
import hu.flowacademy.zetaabsencemanager.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

  Optional<Group> findByIdAndDeletedAtIsNull(Long id);

  List<Group> findAllByDeletedAtIsNull();

  List<Group> findAllByParentId(Long id);

  Optional<Group> findByLeaderAndDeletedAtNull(User leader);
}
