package hu.flowacademy.zetaabsencemanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private Date dateOfBirth;

    @Column
    private Email email;

    @Column
    private Date dateOfEntry;

    @Column
    private Date dateOfEndTrial;

    @Column
    private Boolean isOnTrial;

    // ??? ide kell @Column??
    // ??? A lenti helyett ManyToMany nem lenne jobb? Mert ha vezetői minőségben lesz 
    @ManyToOne
    @JoinColumn
    private Group group;

    @Column
    private String position;

    @Column
    private Integer numberOfChildren;

    @Column
    private String otherAbsenceEnt;

    // ide kell @Column??
    @OneToMany
    @JsonIgnore
    private Absence absence;
    // private List<Absence> absences; ??

}
