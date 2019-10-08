package hu.flowacademy.zetaabsencemanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/admin/department")
public class AdminDepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/:departmentId")
    public Department getOne(@RequestParam Integer departmentId) {
        return departmentService.getUserById(departmentId);
    }

    @GetMapping("")
    public ArrayList<Department> getAll() {
        return departmentService.getAll();
    }

    @PostMapping("")
    public Department createDepartment(@RequestBody Department department) {
        return departmentService.create(department);
    }

    @PutMapping("/:departmentId")
    public Department update(@RequestParam Integer departmentId, @RequestBody Department department) {
        return departmentService.update(departmentId, department);
    }

    @DeleteMapping("")
    public void delete(@RequestParam Integer departmentId) {
        return departmentService.delete(departmentId);
    }
}
