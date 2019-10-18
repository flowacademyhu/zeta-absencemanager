package hu.flowacademy.zetaabsencemanager;

import hu.flowacademy.zetaabsencemanager.model.*;
import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import hu.flowacademy.zetaabsencemanager.repository.GroupRepository;
import hu.flowacademy.zetaabsencemanager.repository.UserRepository;
import hu.flowacademy.zetaabsencemanager.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@Component
@Transactional
public class DataLoader implements CommandLineRunner {

    private final AbsenceRepository absenceRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final GroupService groupService;

    @Autowired
    @Lazy
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(AbsenceRepository absenceRepository,
                      GroupRepository groupRepository,
                      UserRepository userRepository, GroupService groupService) {
        this.absenceRepository = absenceRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.groupService = groupService;
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
                .parentId(cLevel.getId())
                .build();
        this.groupRepository.save(group1);

        Group group2 = Group.builder()
                .employees(List.of())
                .name("Group2")
                .parentId(cLevel.getId())
                .build();
        this.groupRepository.save(group2);

        Group group3 = Group.builder()
                .employees(List.of())
                .name("Group3")
                .parentId(group1.getId())
                .build();
        this.groupRepository.save(group3);

        Group group4 = Group.builder()
                .employees(List.of())
                .name("Group4")
                .parentId(group1.getId())
                .build();
        this.groupRepository.save(group4);


        Group group5 = Group.builder()
                .employees(List.of())
                .name("Group5")
                .parentId(group2.getId())
                .build();
        this.groupRepository.save(group5);


        Group group6 = Group.builder()
                .employees(List.of())
                .name("Group6")
                .parentId(group2.getId())
                .build();
        this.groupRepository.save(group6);

        Group group7 = Group.builder()
                .employees(List.of())
                .name("Group7")
                .parentId(group6.getId())
                .build();
        this.groupRepository.save(group7);

        Group group8 = Group.builder()
                .employees(List.of())
                .name("Group8")
                .parentId(group6.getId())
                .build();
        this.groupRepository.save(group8);


        User admin = User.builder()
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
                .build();
        this.userRepository.save(admin);

        User user1 = User.builder()
                .email("user1@user.com")
                .password(passwordEncoder.encode("user"))
                .firstName("user")
                .lastName("user")
                .role(Roles.LEADER)
                .dateOfBirth(LocalDate.of(1970, Month.FEBRUARY, 28))
                .dateOfEntry(LocalDate.of(2010, Month.MAY, 12))
                .dateOfEndTrial(LocalDate.of(2010, Month.AUGUST, 12))
                .isOnTrial(false)
                .group(cLevel)
                .position("testposition")
                .numberOfChildren(3)
                .otherAbsenceEnt("none")
                .build();
        this.userRepository.save(user1);

        User user2 = User.builder()
                .email("user2@user.com")
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
                .build();
        this.userRepository.save(user2);

        User user3 = User.builder()
                .email("user3@user.com")
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
                .build();
        this.userRepository.save(user3);


        User user4 = User.builder()
                .email("user4@user.com")
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
                .build();
        this.userRepository.save(user4);

        User user5 = User.builder()
                .email("user5@user.com")
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
                .build();
        this.userRepository.save(user5);

        User user6 = User.builder()
                .email("user6@user.com")
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
                .build();
        this.userRepository.save(user6);

        User user7 = User.builder()
                .email("user7@user.com")
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
                .build();
        this.userRepository.save(user7);

        User user8 = User.builder()
                .email("user8@user.com")
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
                .build();
        this.userRepository.save(user8);

        User user9 = User.builder()
                .email("user9@user.com")
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
                .build();
        this.userRepository.save(user9);

        User user10 = User.builder()
                .email("user10@user.com")
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
                .build();
        this.userRepository.save(user10);

        User user11 = User.builder()
                .email("user11@user.com")
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
                .build();
        this.userRepository.save(user11);

        User user12 = User.builder()
                .email("user12@user.com")
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
                .build();
        this.userRepository.save(user12);

        User user13 = User.builder()
                .email("user13@user.com")
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
                .build();
        this.userRepository.save(user13);


        User user14 = User.builder()
                .email("user14@user.com")
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
                .build();
        this.userRepository.save(user14);

        User user15 = User.builder()
                .email("user15@user.com")
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
                .build();
        this.userRepository.save(user15);

        User user16 = User.builder()
                .email("user16@user.com")
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
                .build();
        this.userRepository.save(user16);


        User user17 = User.builder()
                .email("user17@user.com")
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
                .build();
        this.userRepository.save(user17);

        User user18 = User.builder()
                .email("user18@user.com")
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
                .build();
        this.userRepository.save(user18);

        User user19 = User.builder()
                .email("user19@user.com")
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
                .build();
        this.userRepository.save(user19);

        User user20 = User.builder()
                .email("user20@user.com")
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
                .build();
        this.userRepository.save(user20);

        User user21 = User.builder()
                .email("user21@user.com")
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
                .build();
        this.userRepository.save(user21);

        User user22 = User.builder()
                .email("user22@user.com")
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
                .build();
        this.userRepository.save(user22);

        User user23 = User.builder()
                .email("user23@user.com")
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
                .build();
        this.userRepository.save(user23);

        User user24 = User.builder()
                .email("user24@user.com")
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
                .build();
        this.userRepository.save(user24);

        User user25 = User.builder()
                .email("user25@user.com")
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
                .build();
        this.userRepository.save(user25);

        User user26 = User.builder()
                .email("user26@user.com")
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
                .build();
        this.userRepository.save(user26);

        User user27 = User.builder()
                .email("user27@user.com")
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
                .build();
        this.userRepository.save(user27);

        User user28 = User.builder()
                .email("user28@user.com")
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
                .build();
        this.userRepository.save(user28);

        User user29 = User.builder()
                .email("user29@user.com")
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
                .build();
        this.userRepository.save(user29);

        User user30 = User.builder()
                .email("user30@user.com")
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
                .build();
        this.userRepository.save(user30);

        User user31 = User.builder()
                .email("user31@user.com")
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
                .build();
        this.userRepository.save(user31);

        User user32;
        user32 = User.builder()
                .email("user32@user.com")
                .password(passwordEncoder.encode("user"))
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
                .build();
        this.userRepository.save(user32);


        Absence absence1 = Absence.builder()
                .begin(LocalDate.of(2019, Month.OCTOBER, 24))
                .end(LocalDate.of(2019, Month.OCTOBER, 25))
                .reporter(user32)
                .createdAt(LocalDateTime.now())
                .assignee(user28)
                .type(Type.ABSENCE)
                .status(Status.OPEN)
                .build();
        this.absenceRepository.save(absence1);

        Absence absence2 = Absence.builder()
                .begin(LocalDate.of(2019, Month.OCTOBER, 24))
                .end(LocalDate.of(2019, Month.OCTOBER, 25))
                .reporter(user16)
                .assignee(user14)
                .createdAt(LocalDateTime.now())
                .type(Type.NON_WORKING)
                .status(Status.OPEN)
                .build();
        this.absenceRepository.save(absence2);


        Absence absence3 = Absence.builder()
                .begin(LocalDate.of(2019, Month.OCTOBER, 24))
                .end(LocalDate.of(2019, Month.OCTOBER, 25))
                .reporter(user19)
                .assignee(user17)
                .createdAt(LocalDateTime.now())
                .type(Type.CHILD_SICK_PAY)
                .status(Status.OPEN)
                .build();
        this.absenceRepository.save(absence3);

        Absence absence4 = Absence.builder()
                .begin(LocalDate.of(2019, Month.OCTOBER, 24))
                .end(LocalDate.of(2019, Month.OCTOBER, 25))
                .reporter(user13)
                .assignee(user9)
                .createdAt(LocalDateTime.now())
                .type(Type.UNPAID_HOLIDAY)
                .status(Status.OPEN)
                .build();
        this.absenceRepository.save(absence4);

        Absence absence5 = Absence.builder()
                .begin(LocalDate.of(2019, Month.OCTOBER, 24))
                .end(LocalDate.of(2019, Month.OCTOBER, 31))
                .reporter(admin)
                .assignee(admin)
                .createdAt(LocalDateTime.now())
                .status(Status.OPEN)
                .type(Type.ABSENCE)
                .build();
        this.absenceRepository.save(absence5);

        List<User> users=userRepository.findByDeletedAtNull();
        ListIterator<User> it=users.listIterator();
        while (it.hasNext()){
            User user = it.next();
            List<User> leaders=new ArrayList<>();
            if(user.getRole()==Roles.LEADER){
                leaders.add(user);
                List<User> actualLeader=new ArrayList<>();
                actualLeader.add(user);
                user.getGroup().setLeaders(actualLeader);
                Group modified=user.getGroup();
                groupService.updateGroup(user.getGroup().getId(), modified);
            }
        }
    }
}
