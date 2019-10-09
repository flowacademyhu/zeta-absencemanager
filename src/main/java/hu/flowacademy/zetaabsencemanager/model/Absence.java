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
    private Integer id;

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

    // ??? ide kell @ManyToOne Annotáció??
    // ??? ide kell @Column??
    @ManyToOne
    private User reporter;

    // ??? ide kell @ManyToOne Annotáció??
    // ??? ide kell @Column??
    @ManyToOne
    private User assignee;

    @Column
    private Status status;

}
