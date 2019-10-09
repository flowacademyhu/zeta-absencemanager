package hu.flowacademy.zetaabsencemanager.controllers;

import hu.flowacademy.zetaabsencemanager.model.Department;
import hu.flowacademy.zetaabsencemanager.model.Group;
import hu.flowacademy.zetaabsencemanager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/admin/department")
public class AdminDepartmentController {

    //@Autowired
    //private DepartmentService departmentService;

    @GetMapping("/{departmentId}")
    public Department getOne(@PathVariable("departmentId") Integer departmentId) {
        //return departmentService.getUserById(departmentId);
        Department dep=new Department();
        dep.setGroups(new ArrayList<Group>(Arrays.asList()));
        dep.setLeaders(new ArrayList<User>(Arrays.asList()));
        dep.setName("TestDepartment");
        return dep;
    }

    @GetMapping("")
    public ArrayList<Department> getAll() {
        //return departmentService.getAll();
        return new ArrayList<>();
    }

    @PostMapping("")
    public Department createDepartment(@RequestBody Department department) {
        //return departmentService.create(department);
        return department;
    }

    @PutMapping("/{departmentId}")
    public Department update(@PathVariable("departmentId") Integer departmentId,  @RequestBody Department department) {
        //return departmentService.update(departmentId, department);
        return department;
    }

    @DeleteMapping("/{departmentId}")
    public void delete(@PathVariable("departmentId") Integer departmentId) {
        //return departmentService.delete(departmentId);
    }
}
