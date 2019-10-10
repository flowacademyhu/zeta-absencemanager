package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Department;
import hu.flowacademy.zetaabsencemanager.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Transactional
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Optional<Department> findOne(Long id) {
        return departmentRepository.findById(id);
    }

    public Department create(Department department) {
        if (department == null
                || department.getName() == null
                || department.getName().equals("")
                || department.getLeaders().size() == 0
                || department.getLeaders() == null
        ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid.");
        } else {
            departmentRepository.save(department);
        }
        return department;
    }

    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }
}
