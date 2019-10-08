package hu.flowacademy.zetaabsencemanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class CurrentUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/:userId")
    public User getOne(@RequestParam Integer userId) {
        return userService.getUserById(userId);
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
