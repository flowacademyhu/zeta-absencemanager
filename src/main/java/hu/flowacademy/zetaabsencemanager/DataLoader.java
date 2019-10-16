package hu.flowacademy.zetaabsencemanager;

import hu.flowacademy.zetaabsencemanager.model.Group;
import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import hu.flowacademy.zetaabsencemanager.repository.GroupRepository;
import hu.flowacademy.zetaabsencemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Component
@Transactional
public class DataLoader implements CommandLineRunner {

    private final AbsenceRepository absenceRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Autowired
    @Lazy
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(AbsenceRepository absenceRepository,
                      GroupRepository groupRepository,
                      UserRepository userRepository) {
        this.absenceRepository = absenceRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {


        Group group = Group.builder()
                .employees(List.of())
                .name("TestGroup")
                .parentId(null)
                .build();
        this.groupRepository.save(group);

        this.userRepository.save(User.builder()
                .email("admin@admin.com")
                .password(passwordEncoder.encode("admin")) // passwordEncoder.encode("admin")
                .firstName("admin")
                .lastName("admin")
                .role(Roles.ADMIN)
                .dateOfBirth(LocalDate.of(1970, Month.FEBRUARY, 28))
                .dateOfEntry(LocalDate.of(2010, Month.MAY, 12))
                .dateOfEndTrial(LocalDate.of(2010, Month.AUGUST, 12))
                .isOnTrial(false)
                .group(group)
                .position("testposition")
                .numberOfChildren(3)
                .otherAbsenceEnt("none")
                .build()
        );

        this.userRepository.save(User.builder()
                .email("user@user.com")
                .password(passwordEncoder.encode("user")) // passwordEncoder.encode("admin")
                .firstName("user")
                .lastName("user")
                .role(Roles.EMPLOYEE)
                .dateOfBirth(LocalDate.of(1970, Month.FEBRUARY, 28))
                .dateOfEntry(LocalDate.of(2010, Month.MAY, 12))
                .dateOfEndTrial(LocalDate.of(2010, Month.AUGUST, 12))
                .isOnTrial(false)
                .group(group)
                .position("testposition")
                .numberOfChildren(3)
                .otherAbsenceEnt("none")
                .build()
        );
    }
}
