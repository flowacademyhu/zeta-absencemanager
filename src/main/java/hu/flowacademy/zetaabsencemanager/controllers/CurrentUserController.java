package hu.flowacademy.zetaabsencemanager.controllers;

import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.service.AuthenticationService;
import hu.flowacademy.zetaabsencemanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public User delete(@PathVariable("userId") Long userId) {
      System.out.println("USER ID-JA: " + userId);
      return userService.delete(userId);
    }

  @GetMapping("")
  public User getCurrent() {
    return authenticationService.getCurrentUser();
  }

}