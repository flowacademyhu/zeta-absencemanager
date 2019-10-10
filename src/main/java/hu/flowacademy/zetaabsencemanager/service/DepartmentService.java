package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Department;
import hu.flowacademy.zetaabsencemanager.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
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

    public Optional<Department> findOne(Long id){
        return departmentRepository.findById(id);
    }


    public Department updateDempartment(@NotNull Long id, @NotNull Department department) {
        Department modifyDep = findOne(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department not found"));
        if (StringUtils.isEmpty(department.getName()) ||
                CollectionUtils.isEmpty(department.getLeaders()) ||
                CollectionUtils.isEmpty(department.getGroups())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid.");
        } else {
            modifyDep.setName(department.getName());
            modifyDep.setLeaders(department.getLeaders());
            modifyDep.setGroups(department.getGroups());
            departmentRepository.save(modifyDep);
            return modifyDep;
        }
    }

    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }

    public Department create(@NotNull Department department) {
        if (StringUtils.isEmpty(department.getName())
                || CollectionUtils.isEmpty(department.getLeaders())
        ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid.");
        } else {
            departmentRepository.save(department);
        }
        return department;
    }

}
