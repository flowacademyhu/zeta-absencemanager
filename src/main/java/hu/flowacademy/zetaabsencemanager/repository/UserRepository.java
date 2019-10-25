package hu.flowacademy.zetaabsencemanager.repository;

import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmailAndDeletedAtNull(String email);

  Optional<User> findByIdAndDeletedAtNull(Long id);

  List<User> findByDeletedAtNull();

  List<User> findByRoleAndDeletedAtNull(Roles role);
}
