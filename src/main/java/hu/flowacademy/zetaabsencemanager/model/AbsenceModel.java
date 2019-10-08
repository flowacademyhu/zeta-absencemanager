package hu.flowacademy.zetaabsencemanager.model;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Builder
@Data
@Entity
@Table
public class AbsenceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @Column
    private Date createdAt;

    @Column
    private enum Type {
        ABSENCE,
        NON_WORKING,
        CHILD_SICK_PAY,
        UNPAID_HOLIDAY,
    };

    @Column
    private String summary;

    @Column
    private Date begin;

    @Column
    private Date end;

    @Column
    @OneToOne
    private Integer reporterId;

    @Column
    @OneToOne
    private Integer assigneeId;

    @Column
    private enum Status {
        OPEN,
        UNDER_REVIEW,
        APPROVED,
        ADMINISTRATED,
        DONE,
        REJECT
    }

}
