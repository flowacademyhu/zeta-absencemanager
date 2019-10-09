package hu.flowacademy.zetaabsencemanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/admin/user")
public class AdminUsersController {

    //@Autowired
    //private UserService userService;

    @GetMapping("/{userId}")
    public User getOne(@PathVariable("userId") Integer userId) {
        //return userService.getUserById(userId);
    }

    @GetMapping("")
    public ArrayList<User> getAll() {
        //return userService.getAll();
    }

    @PostMapping("")
    public User createUser(@RequestBody User user) {
        //return userService.create(user);
    }

    @PutMapping("/{userId}")
    public User update(@PathVariable("userId") Integer userId, @RequestBody User user) {
        //return userService.update(userId, user);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") Integer userId) {
        //return userService.delete(userID);
    }
}
