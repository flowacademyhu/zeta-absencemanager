package hu.flowacademy.zetaabsencemanager.controllers;

import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.service.AdminUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/user")
public class AdminUsersController {

  @Autowired
  private AdminUserService adminUserService;

  @GetMapping("/{id}")
  public User getOne(@PathVariable("id") Long id) {
    return adminUserService.findOneUser(id);

  }

  @GetMapping("")
  public List<User> getAll() {
    return adminUserService.findAllUser();
  }

  @GetMapping("/employees")
  public List<User> getEmployees() {
    return adminUserService.findAllEmployeesByGroupIsNull();
  }

  @GetMapping("/deleted-employees")
  public List<User> getDeletedEmployees() {
    return adminUserService.findAllDeletedUsers();
  }

  @GetMapping("/employees/{id}")
  public List<User> getEmployeesByGroup(@PathVariable("id") Long id) {
    return adminUserService.findAllEmployeesByGroupId(id);
  }

  @GetMapping("/employees/group/{id}")
  public List<User> getEverybodyFromGroup(@PathVariable("id") Long id) {
    return adminUserService.findEverybodyByGroup(id);
  }

  @PostMapping("")
  public User createUser(@RequestBody User user) {
    return adminUserService.saveUser(user);
  }

  @PutMapping("/{id}")
  public User update(@PathVariable("id") Long id, @RequestBody User user) {
    return adminUserService.updateUser(id, user);

  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") Long id) {
    adminUserService.delete(id);
  }

  @GetMapping("/leaders")
  public List<User> getLeaders() {
    return adminUserService.findAllLeader();
  }

}
