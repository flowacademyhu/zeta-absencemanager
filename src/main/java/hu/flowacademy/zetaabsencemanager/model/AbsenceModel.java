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
    private UserModel reporter; // ManyToOne
    private UserModel assignee;
    private enum status {

    }

    public AbsenceModel(String summary, Date begin, Date end, UserModel reporter, UserModel assignee) {
        this.summary = summary;
        this.begin = begin;
        this.end = end;
        this.reporter = reporter;
        this.assignee = assignee;
    }
}
