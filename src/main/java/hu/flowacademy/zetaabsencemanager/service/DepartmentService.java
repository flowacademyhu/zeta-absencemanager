package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Department;
import hu.flowacademy.zetaabsencemanager.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> findAllDepartments() {
        return departmentRepository.findAll();
    }

    public Optional<Department> findOneDepartment(Long id) {
        return departmentRepository.findById(id);
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    public Department updateDempartment(Long id, Department department) {
        Department modifyDep = findOneDepartment(id).orElse(null);
        modifyDep.setName(department.getName());
        modifyDep.setLeaders(department.getLeaders());
        modifyDep.setGroups(department.getGroups());
        departmentRepository.save(modifyDep);
        return modifyDep;
    }
}
