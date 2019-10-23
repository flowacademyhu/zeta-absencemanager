package hu.flowacademy.zetaabsencemanager.controllers;

import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
public class AdminUsersController {

    @Autowired
    private AdminUserService userService;

    @GetMapping("/{id}")
    public User getOne(@PathVariable("id") Long id) {
        return userService.findOneUser(id);

    }

    @GetMapping("")
    public List<User> getAll() {
        return userService.findAllUser();
    }

    @PostMapping("")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable("id") Long id, @RequestBody User user) {
        return userService.updateUser(id, user);

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    @GetMapping("/leaders")
    public List<User> getLeaders() {
        return userService.findAllLeader();
    }
}
