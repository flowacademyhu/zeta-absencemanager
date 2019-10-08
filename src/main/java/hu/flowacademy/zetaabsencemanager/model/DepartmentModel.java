package hu.flowacademy.zetaabsencemanager.model;

import java.util.List;
import java.util.UUID;

public class DepartmentModel {

    private Integer id;
    private String name;
    private Integer leaderId;
    private Integer groupId;

    public DepartmentModel(String name, Integer leaderId, Integer groupId) {
        this.name = name;
        this.leaderId = leaderId;
        this.groupId = groupId;
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

    public Integer getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Integer leaderId) {
        this.leaderId = leaderId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

}
