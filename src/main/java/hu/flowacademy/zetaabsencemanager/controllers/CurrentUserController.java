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
@RequestMapping("/user")
public class CurrentUserController {

    //@Autowired
    //private UserService userService;

    @GetMapping("/{userId}")
    public User getOne(@PathVariable("userId") Long userId) {
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

    @PutMapping("/{userId}")
    public User update(@PathVariable("userId") Integer userId, @RequestBody User user) {
        //return userService.update(userId, user);
        return user;
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") Integer userId) {
        //return userService.delete(userID);
    }
}
