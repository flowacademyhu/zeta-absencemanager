package hu.flowacademy.zetaabsencemanager.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.flowacademy.zetaabsencemanager.model.StringData;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.service.AuthenticationService;
import hu.flowacademy.zetaabsencemanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    @PutMapping("/changepassw/{userId}")
    public void changePassword(@PathVariable("userId") Long userId, @RequestBody String password) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            StringData datas = mapper.readValue(password, StringData.class);
            userService.changePassword(userId, datas.getDataA(), datas.getDataB());
            datas = null;
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}