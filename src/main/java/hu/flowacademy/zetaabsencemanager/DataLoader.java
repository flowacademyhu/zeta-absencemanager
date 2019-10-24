package hu.flowacademy.zetaabsencemanager;

import hu.flowacademy.zetaabsencemanager.model.Absence;
import hu.flowacademy.zetaabsencemanager.model.Group;
import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.Status;
import hu.flowacademy.zetaabsencemanager.model.Type;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.repository.AbsenceRepository;
import hu.flowacademy.zetaabsencemanager.repository.GroupRepository;
import hu.flowacademy.zetaabsencemanager.repository.UserRepository;
import hu.flowacademy.zetaabsencemanager.service.GroupService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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


        User admin = User.builder()
                .email("admin@admin.com")
                .password(passwordEncoder.encode("admin")) // passwordEncoder.encode("admin")
                .firstName("admin")
                .lastName("admin")
                .role(Roles.ADMIN)
                .dateOfBirth(LocalDate.of(1970, Month.FEBRUARY, 28))
                .dateOfEntry(LocalDate.of(2010, Month.MAY, 12))
                .dateOfEndTrial(LocalDate.of(2010, Month.AUGUST, 12))
                .position("testposition")
                .numberOfChildren(3)
                .build();
        this.userRepository.save(admin);

        List<User> users = new ArrayList<>();
        users.add(admin);
        List<String> firstNames =
                Arrays.asList("Allan", "Anastasia", "Andy", "Arlene", "Beau", "Brianna", "Cara", "Carly", "Carolina", "Chelsea", "Concetta", "Danilo", "Daron", "Darren", "Debbie", "Devin", "Evan", "Frieda", "Gaylord", "Grover", "Irma", "Jon", "Kristopher", "Leonor", "Lorrie", "Meredith", "Mindy", "Newton", "Peggy", "Pete", "Roderick", "Son", "Tristan", "Timoty");


        List<String> lastNames =
                Arrays.asList("Morgan", "Parks", "Pearson", "Wright", "Dorsey", "Moran", "Gallagher", "Hurst", "Bush", "Whitney", "Harper", "Zimmerman", "Butler", "Zavala", "Becker", "Mcpherson", "Stevenson", "Hughes", "Carr", "Washington", "Williamson", "Rich", "Mcguire", "Flynn", "Wang", "Mckenzie", "Palmer", "Camacho", "Dickerson", "Brady", "Potts", "Keller", "Campbell", "Johnson");

        for (int i = 0; i < 31; i++) {
            User user = User.builder()
                    .email("user" + (i + 1) + "@user.com")
                    .firstName(firstNames.get(i))
                    .lastName(lastNames.get(i))
                    .role(Roles.EMPLOYEE)
                    .dateOfBirth(LocalDate.of(1970, Month.APRIL, 1))
                    .dateOfEntry(LocalDate.of(2010, Month.MAY, 12))
                    .dateOfEndTrial(LocalDate.of(2010, Month.AUGUST, 12))
                    .password(passwordEncoder.encode("user"))
                    .position("testposition")
                    .numberOfChildren(3)
                    .build();
            this.userRepository.save(user);
            users.add(user);
        }

        Group group1 = Group.builder()
                .employees(List.of())
                .name("Group1")
                .leader(userRepository.getOne(2L))
                .build();
        groupRepository.save(group1);
        List<User> g1u = Arrays.asList(users.get(0), users.get(2), users.get(3));
        groupRepository.findById(group1.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Group not found."))
                .setEmployees(g1u);
        for (User user : g1u) {
            user.setGroup(group1);
            userRepository.save(user);
        }


        Group group2 = Group.builder()
                .employees(List.of())
                .name("Group2")
                .leader(userRepository.getOne(3L))
                .parentId(group1.getId())
                .build();
        groupRepository.save(group2);
        List<User> g2u = Arrays.asList(users.get(4), users.get(5));
        groupRepository.findById(group2.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Group not found."))
                .setEmployees(g2u);
        for (User user : g2u) {
            user.setGroup(group2);
            userRepository.save(user);
        }


        Group group3 = Group.builder()
                .employees(List.of())
                .name("Group3")
                .leader(userRepository.getOne(4L))
                .parentId(group1.getId())
                .build();
        this.groupRepository.save(group3);
        List<User> g3u = Arrays.asList(users.get(6), users.get(7));
        this.groupRepository.findById(group3.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Group not found."))
                .setEmployees(g3u);
        for (User user : g3u) {
            user.setGroup(group3);
            userRepository.save(user);
        }

        Group group4 = Group.builder()
                .employees(List.of())
                .name("Group4")
                .leader(userRepository.getOne(5L))
                .parentId(group2.getId())
                .build();
        this.groupRepository.save(group4);
        List<User> g4u = Arrays.asList(users.get(8), users.get(9), users.get(10), users.get(11), users.get(12), users.get(13));
        this.groupRepository.findById(group4.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Group not found."))
                .setEmployees(g4u);
        for (User user : g4u) {
            user.setGroup(group4);
            userRepository.save(user);
        }


        Group group5 = Group.builder()
                .employees(List.of())
                .name("Group5")
                .leader(userRepository.getOne(6L))
                .parentId(group2.getId())
                .build();
        this.groupRepository.save(group5);
        List<User> g5u = Arrays.asList(users.get(14), users.get(15), users.get(16), users.get(17), users.get(18), users.get(19));
        this.groupRepository.findById(group5.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Group not found."))
                .setEmployees(g5u);
        for (User user : g5u) {
            user.setGroup(group5);
            userRepository.save(user);
        }


        Group group6 = Group.builder()
                .employees(List.of())
                .name("Group6")
                .leader(userRepository.getOne(7L))
                .parentId(group3.getId())
                .build();
        this.groupRepository.save(group6);
        List<User> g6u = Arrays.asList(users.get(20), users.get(21), users.get(22), users.get(23), users.get(24), users.get(25));
        this.groupRepository.findById(group6.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Group not found."))
                .setEmployees(g6u);
       for (User user : g6u) {
            user.setGroup(group6);
            userRepository.save(user);
        }

        Group group7 = Group.builder()
                .employees(List.of())
                .name("Group7")
                .leader(userRepository.getOne(8L))
                .parentId(group3.getId())
                .build();
        this.groupRepository.save(group7);
        List<User> g7u = Arrays.asList(users.get(26), users.get(27), users.get(28), users.get(29), users.get(30), users.get(31));
        this.groupRepository.findById(group7.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Group not found."))
                .setEmployees(g7u);
        for (User user : g7u) {
            user.setGroup(group7);
            userRepository.save(user);
        }


        Absence absence1 = Absence.builder()
                .begin(LocalDate.of(2019, Month.OCTOBER, 24))
                .end(LocalDate.of(2019, Month.OCTOBER, 25))
                .reporter(users.get(11))
                .createdAt(LocalDateTime.now())
                .assignee(users.get(11).getGroup().getLeader())
                .type(Type.ABSENCE)
                .status(Status.OPEN)
                .duration(2)
                .build();
        this.absenceRepository.save(absence1);

        Absence absence2 = Absence.builder()
                .begin(LocalDate.of(2019, Month.OCTOBER, 24))
                .end(LocalDate.of(2019, Month.OCTOBER, 25))
                .reporter(users.get(17))
                .assignee(users.get(17).getGroup().getLeader())
                .createdAt(LocalDateTime.now())
                .type(Type.NON_WORKING)
                .duration(2)
                .status(Status.OPEN)
                .build();
        this.absenceRepository.save(absence2);

        Absence absence3 = Absence.builder()
                .begin(LocalDate.of(2019, Month.OCTOBER, 24))
                .end(LocalDate.of(2019, Month.OCTOBER, 25))
                .reporter(users.get(21))
                .assignee(users.get(21).getGroup().getLeader())
                .createdAt(LocalDateTime.now())
                .status(Status.OPEN)
                .duration(2)
                .type(Type.CHILD_SICK_PAY)
                .build();
        this.absenceRepository.save(absence3);

        Absence absence4 = Absence.builder()
                .begin(LocalDate.of(2019, Month.OCTOBER, 24))
                .end(LocalDate.of(2019, Month.OCTOBER, 25))
                .reporter(users.get(27))
                .assignee(users.get(27).getGroup().getLeader())
                .createdAt(LocalDateTime.now())
                .status(Status.OPEN)
                .duration(2)
                .type(Type.UNPAID_HOLIDAY)
                .build();
        this.absenceRepository.save(absence4);

        Absence absence5 = Absence.builder()
                .begin(LocalDate.of(2019, Month.OCTOBER, 24))
                .end(LocalDate.of(2019, Month.OCTOBER, 31))
                .reporter(admin)
                .assignee(admin.getGroup().getLeader())
                .createdAt(LocalDateTime.now())
                .status(Status.OPEN)
                .type(Type.ABSENCE)
                .duration(6)
                .build();
        this.absenceRepository.save(absence5);
    }
}