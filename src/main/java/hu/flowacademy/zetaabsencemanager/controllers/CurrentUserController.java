package hu.flowacademy.zetaabsencemanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/user")
public class CurrentUserController {

    //@Autowired
    //private UserService userService;

    @GetMapping("/{userId}")
    public User getOne(@PathVariable("userId") Integer userId) {
        //return userService.getUserById(userId);
        User user = new User("József", "Kiss", new LocalDate(1970, 2,28), "jozsef.kiss@test.com", new LocalDate (2010,  5, 12), new LocalDate (2010,  8, 12),  false, new ArrayList<String>(Arrays.asList("group1", "group2")), new ArrayList<String>(Arrays.asList("dep1", "dep2")), "testposition", "role", 3, "none");
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
