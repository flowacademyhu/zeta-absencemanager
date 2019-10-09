package hu.flowacademy.zetaabsencemanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/admin/group")
public class AdminGroupController {

    //@Autowired
    //private GroupService groupService;

    @GetMapping("/{groupId}")
    public Group getOne(@PathVariable("groupId") Integer groupId) {
        //return groupService.getGroupById(groupId);
    }

    @GetMapping("")
    public ArrayList<Group> getAll() {
        //return groupService.getAll();
    }

    @PostMapping("")
    public Group createGroup(@RequestBody Group group) {
        //return groupService.create(group);
    }

    @PutMapping("/{departmentId}")
    public Group update(@PathVariable("departmentId") Integer groupId, @RequestBody Group group) {
        //return groupService.update(groupId, group);
    }

    @DeleteMapping("/{departmentId}")
    public void delete(@PathVariable("departmentId") Integer groupId) {
        //return groupService.delete(groupId);
    }

}
