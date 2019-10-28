package hu.flowacademy.zetaabsencemanager.controllers;

import hu.flowacademy.zetaabsencemanager.model.PasswordChangeDTO;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.service.AuthenticationService;
import hu.flowacademy.zetaabsencemanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class CurrentUserController {


    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/{userId}")
    public User getOne(@PathVariable("userId") Long userId) {
        return userService.findOneUser(userId);
    }

    @PutMapping("/{userId}")
    public User update(@PathVariable("userId") Long userId, @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") Long userId) {
        userService.delete(userId);
    }

    @GetMapping("")
    public User getCurrent() {
        return authenticationService.getCurrentUser();
    }

    @PostMapping("/changepassw")
    public User changePassword(@RequestBody PasswordChangeDTO passwords){
        return userService.changePassword(passwords.getFirstPassword(), passwords.getSecondPassword(), passwords.getOldPassword());
    }
}