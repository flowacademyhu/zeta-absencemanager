package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Department;
import hu.flowacademy.zetaabsencemanager.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
        if (modifyDep == null ||
                department.getName() == null ||
                department.getName().equals("") ||
                department.getLeaders() == null ||
                department.getLeaders().size() == 0 ||
                department.getGroups() == null ||
                department.getGroups().size() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid.");
        } else {
            modifyDep.setName(department.getName());
            modifyDep.setLeaders(department.getLeaders());
            modifyDep.setGroups(department.getGroups());
            departmentRepository.save(modifyDep);
            return modifyDep;
        }
    }
}
