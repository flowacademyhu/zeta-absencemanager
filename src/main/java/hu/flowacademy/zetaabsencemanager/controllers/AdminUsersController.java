package hu.flowacademy.zetaabsencemanager.controllers;

import hu.flowacademy.zetaabsencemanager.model.Department;
import hu.flowacademy.zetaabsencemanager.model.Group;
import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/admin/user")
public class AdminUsersController {

    //@Autowired
    //private UserService userService;

    @GetMapping("/{id}")
    public User getOne(@PathVariable("id") Long id) {
        //return userService.getUserById(userId);
        Department dep = Department.builder()
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
        return user;
    }

    @GetMapping("")
    public ArrayList<User> getAll() {
        //return userService.getAll();
        return new ArrayList<>();
    }

    @PostMapping("")
    public User createUser(@RequestBody User user) {
        //return userService.create(user);
        return user;
    }

    @PutMapping("/{id}")
    public User update(@PathVariable("id") Long id, @RequestBody User user) {
        //return userService.update(id, user);
        return user;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        //return userService.delete(userID);
    }
}
