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
import java.util.List;

@RestController
@RequestMapping("/admin/group")
public class AdminGroupController {

    //@Autowired
    //private GroupService groupService;

    @GetMapping("/{id}")
    public Group getOne(@PathVariable("id") Long id) {
        //return groupService.getGroupById(groupId);

        Group group = Group.builder().build();
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

    @PutMapping("/{id}")
    public Group update(@PathVariable("id") Long id, @RequestBody Group group) {
        //return groupService.update(id, group);
        return group;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        //return groupService.delete(id);
    }

}
