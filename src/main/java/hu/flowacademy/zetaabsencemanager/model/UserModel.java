package hu.flowacademy.zetaabsencemanager.model;

import javax.validation.constraints.Email;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

public class UserModel {

    private Integer id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Email email;
    private Date dateOfEntry;
    private Date dateOfEndTrial;
    private Boolean isOnTrial;
    private Integer groupId;
    private String position;
    private Integer leaderId;
    private Integer numberOfChildren;
    private String otherAbsenceEnt;

    private Integer absenceId;


    public UserModel(String firstName, String lastName, Date dateOfBirth, Email email, Date dateOfEntry, Date dateOfEndTrial, Integer groupId, String position, Integer leaderId, Integer numberOfChildren, String otherAbsenceEnt, Integer childSickPay) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.dateOfEntry = dateOfEntry;
        this.dateOfEndTrial = dateOfEndTrial;
        this.groupId = groupId;
        this.position = position;
        this.leaderId = leaderId;
        this.numberOfChildren = numberOfChildren;
        this.otherAbsenceEnt = otherAbsenceEnt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Date getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(Date dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }

    public Date getDateOfEndTrial() {
        return dateOfEndTrial;
    }

    public void setDateOfEndTrial(Date dateOfEndTrial) {
        this.dateOfEndTrial = dateOfEndTrial;
    }

    public Boolean getOnTrial() {
        return isOnTrial;
    }

    public void setOnTrial(Boolean onTrial) {
        isOnTrial = onTrial;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Integer leaderId) {
        this.leaderId = leaderId;
    }

    public Integer getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(Integer numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public String getOtherAbsenceEnt() {
        return otherAbsenceEnt;
    }

    public void setOtherAbsenceEnt(String otherAbsenceEnt) {
        this.otherAbsenceEnt = otherAbsenceEnt;
    }

}
