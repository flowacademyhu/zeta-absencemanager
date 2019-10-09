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

@RestController
@RequestMapping("/user")
public class CurrentUserController {

    //@Autowired
    //private UserService userService;

    @GetMapping("/{userId}")
    public User getOne(@PathVariable("userId") Long userId) {
        //return userService.getUserById(userId);
        User user = new User();
        user.setFirstName("József");
        user.setLastName("Kiss");
        user.setEmail("jozsef.kiss@test.com");
        user.setPassword("test");
        user.setDateOfBirth(LocalDate.of(1970, Month.FEBRUARY, 28));
        user.setDateOfEntry(LocalDate.of(2010, Month.MAY, 12));
        user.setDateOfEndTrial(LocalDate.of(2010, Month.AUGUST, 12));
        user.setIsOnTrial(false);
        Group group1 = new Group();
        user.setGroups(new ArrayList<Group>(Arrays.asList(group1)));
        Department dep1=new Department();
        user.setDepartments(new ArrayList<Department>(Arrays.asList(dep1)));
        user.setPosition("testposition");
        user.setRole(Roles.EMPLOYEE);
        user.setNumberOfChildren(3);
        user.setOtherAbsenceEnt("none");
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
