package hu.flowacademy.zetaabsencemanager.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Data
@Entity
@Table
public class Absence {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column
    private LocalDateTime createdAt;

    @Column
    private Type type;

    @Column
    private String summary;

    @Column
    private Date begin;

    @Column
    private Date end;

    @ManyToOne
    private User reporter;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User assignee;

    @Column
    private Status status;

}
