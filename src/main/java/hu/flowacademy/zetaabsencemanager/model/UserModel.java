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
    private GroupModel groupModel;
    private String position;
    private String leader;
    private Integer numberOfChildren;
    private String otherAbsenceEnt;

    private List<AbsenceModel> absences;
    private Integer totalAbsences;
    private Integer availableAbsences;
    private Double proratedTakenAbsence;

    private Integer totalSickAbsence;
    private Integer takenSickAbsence;
    private Integer availableSickAbsence;

    private Integer takenSickPay;
    // private Double sickAbsenceSickPayRate;

    private Integer childSickPay;

    public UserModel(String firstName, String lastName, Date dateOfBirth, Email email, Date dateOfEntry, Date dateOfEndTrial, GroupModel groupModel, String position, Integer numberOfChildren, String otherAbsenceEnt, Integer childSickPay) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.dateOfEntry = dateOfEntry;
        this.dateOfEndTrial = dateOfEndTrial;
        this.groupModel = groupModel;
        this.position = position;
        this.numberOfChildren = numberOfChildren;
        this.otherAbsenceEnt = otherAbsenceEnt;
        this.childSickPay = childSickPay;
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

    public GroupModel getGroupModel() {
        return groupModel;
    }

    public void setGroupModel(GroupModel groupModel) {
        this.groupModel = groupModel;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
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

    public List<AbsenceModel> getAbsences() {
        return absences;
    }

    public void setAbsences(List<AbsenceModel> absences) {
        this.absences = absences;
    }

    public Integer getTotalAbsences() {
        return totalAbsences;
    }

    public void setTotalAbsences(Integer totalAbsences) {
        this.totalAbsences = totalAbsences;
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
