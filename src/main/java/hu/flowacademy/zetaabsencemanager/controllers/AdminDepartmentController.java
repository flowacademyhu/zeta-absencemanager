package hu.flowacademy.zetaabsencemanager.controllers;

import hu.flowacademy.zetaabsencemanager.model.Department;
import hu.flowacademy.zetaabsencemanager.model.Group;
import hu.flowacademy.zetaabsencemanager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/admin/department")
public class AdminDepartmentController {

    //@Autowired
    //private DepartmentService departmentService;

    @GetMapping("/{id}")
    public Department getOne(@PathVariable("id") Long id) {
        //return departmentService.getUserById(id);
        Department dep=Department.builder().build();
        return dep;
    }

    @GetMapping("")
    public ArrayList<Department> getAll() {
        //return departmentService.findAllDepartments();
        return new ArrayList<>();
    }

    @PostMapping("")
    public Department createDepartment(@RequestBody Department department) {
        //return departmentService.create(department);
        return department;
    }

    @PutMapping("/{id}")
    public Department update(@PathVariable("id") Integer id,  @RequestBody Department department) {
        //return departmentService.update(id, department);
        return department;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        //return departmentService.delete(id);
    }
}
