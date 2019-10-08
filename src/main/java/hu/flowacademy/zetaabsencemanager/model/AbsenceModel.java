package hu.flowacademy.zetaabsencemanager.model;

import java.sql.Date;

public class AbsenceModel {

    private Integer id;
    private Date createdAt;


    private enum type {

    };

    private String summary;
    private Date begin;
    private Date end;
    private Integer reporterId; // ManyToOne
    private Integer assigneeId;
    private enum status {

    }

    public AbsenceModel(String summary, Date begin, Date end, Integer reporterId, Integer assigneeId) {
        this.summary = summary;
        this.begin = begin;
        this.end = end;
        this.reporterId = reporterId;
        this.assigneeId = assigneeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Integer getReporterId() {
        return reporterId;
    }

    public void setReporterId(Integer reporterId) {
        this.reporterId = reporterId;
    }

    public Integer getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Integer assigneeId) {
        this.assigneeId = assigneeId;
    }
}
