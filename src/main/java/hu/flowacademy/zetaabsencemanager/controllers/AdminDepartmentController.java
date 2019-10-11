package hu.flowacademy.zetaabsencemanager.controllers;

import hu.flowacademy.zetaabsencemanager.model.Department;
import hu.flowacademy.zetaabsencemanager.model.Group;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*import java.util.ArrayList;
import java.util.Arrays;*/
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/department")
public class AdminDepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/{id}")
    public Optional<Department> getOne(@PathVariable("id") Long id) {
        return departmentService.findOne(id);
    }

    @GetMapping("")
    public List<Department> getAll() {
        return departmentService.findAllDepartments();
    }

    @PostMapping("")
    public Department createDepartment(@RequestBody Department department) {
        return departmentService.create(department);
        //return department;
    }

    @PutMapping("/{id}")
    public Department update(@PathVariable("id") Long id,  @RequestBody Department department) {
        return departmentService.updateDempartment(id, department);
        //return department;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        departmentService.delete(id);
    }
}
