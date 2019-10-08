package hu.flowacademy.zetaabsencemanager.model;

import java.util.List;
import java.util.UUID;

public class DepartmentModel {

    private Integer id;
    private String name;
    private String leader;
    private List<GroupModel> groups;

    private Integer totalAbsences;
    private Integer takenAbsences;
    private Integer availableAbsences;
    private Double proratedTakenAbsence;

    private Integer totalSickAbsence;
    private Integer takenSickAbsence;
    private Integer availableSickAbsence;

    private Integer takenSickPay;
    // private Double sickAbsenceSickPayRate;

    private Integer childSickPay;

    public DepartmentModel(String name, String leader) {
        this.name = name;
        this.leader = leader;
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

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public List<GroupModel> getUsers() {
        return groups;
    }

    public void setUsers(List<GroupModel> users) {
        this.groups = users;
    }

    public Integer getTotalAbsences() {
        return totalAbsences;
    }

    public void setTotalAbsences(Integer totalAbsences) {
        this.totalAbsences = totalAbsences;
    }

    public Integer getTakenAbsences() {
        return takenAbsences;
    }

    public void setTakenAbsences(Integer takenAbsences) {
        this.takenAbsences = takenAbsences;
    }

    public Integer getAvailableAbsences() {
        return availableAbsences;
    }

    public void setAvailableAbsences(Integer availableAbsences) {
        this.availableAbsences = availableAbsences;
    }

    public Double getProratedTakenAbsence() {
        return proratedTakenAbsence;
    }

    public void setProratedTakenAbsence(Double proratedTakenAbsence) {
        this.proratedTakenAbsence = proratedTakenAbsence;
    }

    public Integer getTotalSickAbsence() {
        return totalSickAbsence;
    }

    public void setTotalSickAbsence(Integer totalSickAbsence) {
        this.totalSickAbsence = totalSickAbsence;
    }

    public Integer getTakenSickAbsence() {
        return takenSickAbsence;
    }

    public void setTakenSickAbsence(Integer takenSickAbsence) {
        this.takenSickAbsence = takenSickAbsence;
    }

    public Integer getAvailableSickAbsence() {
        return availableSickAbsence;
    }

    public void setAvailableSickAbsence(Integer availableSickAbsence) {
        this.availableSickAbsence = availableSickAbsence;
    }

    public Integer getTakenSickPay() {
        return takenSickPay;
    }

    public void setTakenSickPay(Integer takenSickPay) {
        this.takenSickPay = takenSickPay;
    }

    public Integer getChildSickPay() {
        return childSickPay;
    }

    public void setChildSickPay(Integer childSickPay) {
        this.childSickPay = childSickPay;
    }
}
