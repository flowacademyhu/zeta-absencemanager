package hu.flowacademy.zetaabsencemanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/admin/user")
public class AdminUsersController {

    @Autowired
    private UserService userService;

    @GetMapping("/:userId")
    public User getOne(@RequestParam Integer userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("")
    public ArrayList<User> getAll() {
        return userService.getAll();
    }

    @PostMapping("")
    public User createUser() {
        return userService.create();
    }

    @PutMapping("/:userId")
    public User update(@RequestParam Integer userId, @RequestBody User user) {
        return userService.update(userId, user);
    }

    @DeleteMapping("")
    public void delete(@RequestParam Integer userID) {
        return userService.delete(userID);
    }
}
