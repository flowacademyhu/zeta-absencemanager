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


        Group cLevel = Group.builder()
                .employees(List.of())
                .name("C-level")
                .parentId(null)
                .build();
        this.groupRepository.save(cLevel);

        Group group1 = Group.builder()
                .employees(List.of())
                .name("Group1")
                .parentId(1L)
                .build();
        this.groupRepository.save(group1);

        Group group2 = Group.builder()
                .employees(List.of())
                .name("Group2")
                .parentId(1L)
                .build();
        this.groupRepository.save(group2);

        Group group3 = Group.builder()
                .employees(List.of())
                .name("Group3")
                .parentId(2L)
                .build();
        this.groupRepository.save(group3);

        Group group4 = Group.builder()
                .employees(List.of())
                .name("Group4")
                .parentId(2L)
                .build();
        this.groupRepository.save(group4);


        Group group5 = Group.builder()
                .employees(List.of())
                .name("Group5")
                .parentId(3L)
                .build();
        this.groupRepository.save(group5);


        Group group6 = Group.builder()
                .employees(List.of())
                .name("Group6")
                .parentId(3L)
                .build();
        this.groupRepository.save(group6);

        Group group7 = Group.builder()
                .employees(List.of())
                .name("Group7")
                .parentId(7L)
                .build();
        this.groupRepository.save(group7);

        Group group8 = Group.builder()
                .employees(List.of())
                .name("Group8")
                .parentId(7L)
                .build();
        this.groupRepository.save(group8);



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
                .group(cLevel)
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
                .group(cLevel)
                .position("testposition")
                .numberOfChildren(3)
                .otherAbsenceEnt("none")
                .build()
        );

        this.userRepository.save(User.builder()
                .email("testuser1@user.com")
                .password(passwordEncoder.encode("user")) // passwordEncoder.encode("admin")
                .firstName("user1")
                .lastName("user1")
                .role(Roles.EMPLOYEE)
                .dateOfBirth(LocalDate.of(1970, Month.FEBRUARY, 28))
                .dateOfEntry(LocalDate.of(2010, Month.MAY, 12))
                .dateOfEndTrial(LocalDate.of(2010, Month.AUGUST, 12))
                .isOnTrial(false)
                .group(cLevel)
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
                .role(Roles.LEADER)
                .dateOfBirth(LocalDate.of(1970, Month.FEBRUARY, 28))
                .dateOfEntry(LocalDate.of(2010, Month.MAY, 12))
                .dateOfEndTrial(LocalDate.of(2010, Month.AUGUST, 12))
                .isOnTrial(false)
                .group(group7)
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
                .group(group7)
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
                .group(group7)
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
                .role(Roles.LEADER)
                .dateOfBirth(LocalDate.of(1970, Month.FEBRUARY, 28))
                .dateOfEntry(LocalDate.of(2010, Month.MAY, 12))
                .dateOfEndTrial(LocalDate.of(2010, Month.AUGUST, 12))
                .isOnTrial(false)
                .group(group8)
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
                .group(group8)
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
                .group(group8)
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
                .role(Roles.LEADER)
                .dateOfBirth(LocalDate.of(1970, Month.FEBRUARY, 28))
                .dateOfEntry(LocalDate.of(2010, Month.MAY, 12))
                .dateOfEndTrial(LocalDate.of(2010, Month.AUGUST, 12))
                .isOnTrial(false)
                .group(group6)
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
                .group(group6)
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
                .group(group6)
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
                .group(group6)
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
                .group(group6)
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
                .role(Roles.LEADER)
                .dateOfBirth(LocalDate.of(1970, Month.FEBRUARY, 28))
                .dateOfEntry(LocalDate.of(2010, Month.MAY, 12))
                .dateOfEndTrial(LocalDate.of(2010, Month.AUGUST, 12))
                .isOnTrial(false)
                .group(group5)
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
                .group(group5)
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
                .group(group5)
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
                .role(Roles.LEADER)
                .dateOfBirth(LocalDate.of(1970, Month.FEBRUARY, 28))
                .dateOfEntry(LocalDate.of(2010, Month.MAY, 12))
                .dateOfEndTrial(LocalDate.of(2010, Month.AUGUST, 12))
                .isOnTrial(false)
                .group(group4)
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
                .group(group4)
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
                .group(group4)
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
                .role(Roles.LEADER)
                .dateOfBirth(LocalDate.of(1970, Month.FEBRUARY, 28))
                .dateOfEntry(LocalDate.of(2010, Month.MAY, 12))
                .dateOfEndTrial(LocalDate.of(2010, Month.AUGUST, 12))
                .isOnTrial(false)
                .group(group3)
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
                .group(group3)
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
                .group(group3)
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
                .role(Roles.LEADER)
                .dateOfBirth(LocalDate.of(1970, Month.FEBRUARY, 28))
                .dateOfEntry(LocalDate.of(2010, Month.MAY, 12))
                .dateOfEndTrial(LocalDate.of(2010, Month.AUGUST, 12))
                .isOnTrial(false)
                .group(group2)
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
                .group(group2)
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
                .group(group2)
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
                .group(group2)
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
                .group(group2)
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
                .role(Roles.LEADER)
                .dateOfBirth(LocalDate.of(1970, Month.FEBRUARY, 28))
                .dateOfEntry(LocalDate.of(2010, Month.MAY, 12))
                .dateOfEndTrial(LocalDate.of(2010, Month.AUGUST, 12))
                .isOnTrial(false)
                .group(group1)
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
                .group(group1)
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
                .group(group1)
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
                .group(group1)
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
                .group(group1)
                .position("testposition")
                .numberOfChildren(3)
                .otherAbsenceEnt("none")
                .build()
        );
    }


}
