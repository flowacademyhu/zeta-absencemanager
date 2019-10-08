package hu.flowacademy.zetaabsencemanager.model;

import java.util.List;
import java.util.UUID;

public class GroupModel {

    private Integer id;
    private String name;
    private Integer leaderId;
    private Integer departmentId;
    private Integer employeeId;

    public GroupModel(String name, Integer leaderId, Integer departmentId) {
        this.name = name;
        this.leaderId = leaderId;
        this.departmentId = departmentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Integer leaderId) {
        this.leaderId = leaderId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
}


