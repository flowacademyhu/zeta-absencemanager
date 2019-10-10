package hu.flowacademy.zetaabsencemanager;

import hu.flowacademy.zetaabsencemanager.model.Department;
import hu.flowacademy.zetaabsencemanager.model.Group;
import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import hu.flowacademy.zetaabsencemanager.repository.DepartmentRepository;
import hu.flowacademy.zetaabsencemanager.repository.GroupRepository;
import hu.flowacademy.zetaabsencemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Component
@Transactional
public class DataLoader implements CommandLineRunner {

    private final AbsenceRepository absenceRepository;
    private final DepartmentRepository departmentRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Autowired
    public DataLoader(AbsenceRepository absenceRepository,
                      DepartmentRepository departmentRepository,
                      GroupRepository groupRepository,
                      UserRepository userRepository) {
        this.absenceRepository = absenceRepository;
        this.departmentRepository = departmentRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Department dep = Department.builder()
                .groups(List.of())
                .leaders(List.of())
                .name("TestDepartment")
                .build();
        this.departmentRepository.save(dep);

        Group group = Group.builder()
                .department(dep)
                .employees(List.of())
                .name("TestGroup")
                .build();
        this.groupRepository.save(group);

        this.userRepository.save( User.builder()
                .email("admin@admin.com")
                .password("YWRtaW4=") // passwordEncoder.encode("admin")
                .firstName("admin")
                .lastName("admin")
                .role(Roles.ADMIN)
                .dateOfBirth(LocalDate.of(1970, Month.FEBRUARY, 28))
                .dateOfEntry(LocalDate.of(2010, Month.MAY, 12))
                .dateOfEndTrial(LocalDate.of(2010, Month.AUGUST, 12))
                .isOnTrial(false)
                .groups(List.of(group))
                .departments(List.of(dep))
                .position("testposition")
                .numberOfChildren(3)
                .otherAbsenceEnt("none")
                .build()
        );
    }
}
