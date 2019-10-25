package hu.flowacademy.zetaabsencemanager.controllers;


import hu.flowacademy.zetaabsencemanager.model.Group;
import hu.flowacademy.zetaabsencemanager.service.GroupService;
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
@RequestMapping("/admin/group")
public class AdminGroupController {

  @Autowired
  private GroupService groupService;

  @GetMapping("/{id}")
  public Group getOne(@PathVariable("id") Long id) {
    return groupService.findOne(id);

  }

  @GetMapping("")
  public List<Group> getAll() {
    return groupService.findAllGroup();

  }

  @PostMapping("")
  public Group createGroup(@RequestBody Group group) {
    return groupService.create(group);

  }

  @PutMapping("/{id}")
  public Group update(@PathVariable("id") Long id, @RequestBody Group group) {
    return groupService.updateGroup(id, group);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") Long id) {
    groupService.delete(id);
  }

}
