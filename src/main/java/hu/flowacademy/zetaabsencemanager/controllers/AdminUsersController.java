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
@RequestMapping("/admin/user")
public class AdminUsersController {

    //@Autowired
    //private UserService userService;

    @GetMapping("/{id}")
    public User getOne(@PathVariable("id") Long id) {
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
