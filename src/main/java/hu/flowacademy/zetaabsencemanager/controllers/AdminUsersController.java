package hu.flowacademy.zetaabsencemanager.controllers;

import hu.flowacademy.zetaabsencemanager.model.Group;
import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.service.AdminUserService;
import hu.flowacademy.zetaabsencemanager.utils.PageableDTO;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  public PageableDTO<User> getAll(Pageable pageable,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfEntryStart,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfEntryFinish,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfEndTrialStart,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfEndTrialFinish,
      @RequestParam(required = false) Group group,
      @RequestParam(required = false) String position,
      @RequestParam(required = false) Roles role) {
    return adminUserService
        .findAllUserFilterPagination(name, dateOfEntryStart, dateOfEntryFinish, dateOfEndTrialStart,
            dateOfEndTrialFinish, group, position, role, pageable);
  }

  @GetMapping("/employees")
  public List<User> getEmployees() {
    return adminUserService.findAllEmployeesByGroupIsNull();
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
