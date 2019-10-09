package hu.flowacademy.zetaabsencemanager.controllers;

import hu.flowacademy.zetaabsencemanager.model.Department;
import hu.flowacademy.zetaabsencemanager.model.Group;
import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/admin/group")
public class AdminGroupController {

    //@Autowired
    //private GroupService groupService;

    @GetMapping("/{groupId}")
    public Group getOne(@PathVariable("groupId") Integer groupId) {
        //return groupService.getGroupById(groupId);
        Group group = new Group();
        group.setDepartment(new Department());
        group.setEmployees(new ArrayList<User>(Arrays.asList()));
        return group;
    }

    @GetMapping("")
    public ArrayList<Group> getAll() {
        //return groupService.getAll();
        return new ArrayList<>();
    }

    @PostMapping("")
    public Group createGroup(@RequestBody Group group) {
        //return groupService.create(group);
        return group;
    }

    @PutMapping("/{departmentId}")
    public Group update(@PathVariable("departmentId") Integer groupId, @RequestBody Group group) {
        //return groupService.update(groupId, group);
        return group;
    }

    @DeleteMapping("/{departmentId}")
    public void delete(@PathVariable("departmentId") Integer groupId) {
        //return groupService.delete(groupId);
    }

}
