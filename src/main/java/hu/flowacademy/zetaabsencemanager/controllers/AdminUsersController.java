package hu.flowacademy.zetaabsencemanager.controllers;

import hu.flowacademy.zetaabsencemanager.auth.CustomUserDetailsService;
import hu.flowacademy.zetaabsencemanager.model.Department;
import hu.flowacademy.zetaabsencemanager.model.Group;
import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/user")
public class AdminUsersController {

    @Autowired
    private AdminUserService userService;


    @GetMapping("/{id}")
    public Optional<User> getOne(@PathVariable("id") Long id) {
        return userService.findOneUser(id);
        /*Department dep = Department.builder()
                .groups(List.of())
                .leaders(List.of())
                .name("TestDepartment")
                .build();
        Group group = Group.builder()
                .department(dep)
                .employees(List.of())
                .name("TestGroup")
                .build();

        User user = User.builder()
                .firstName("József")
                .lastName("Kiss")
                .email("jozsef.kiss@test.com")
                .password("test")
                .dateOfBirth(LocalDate.of(1970, Month.FEBRUARY, 28))
                .dateOfEntry(LocalDate.of(2010, Month.MAY, 12))
                .dateOfEndTrial(LocalDate.of(2010, Month.AUGUST, 12))
                .isOnTrial(false)
                .groups(List.of(group))
                .departments(List.of(dep))
                .position("testposition")
                .role(Roles.EMPLOYEE)
                .numberOfChildren(3)
                .otherAbsenceEnt("none")
                .build();
        return user;*/
    }

    @GetMapping("")
    public List<User> getAll() {
        return userService.findAllUser();
        //return new ArrayList<>();
    }

    @PostMapping("")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable("id") Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
        //return user;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        return userService.delete(id);
    }
}
