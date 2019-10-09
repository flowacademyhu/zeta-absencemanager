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
    private String email;

    @Column
    private Date dateOfEntry;

    @Column
    private Date dateOfEndTrial;

    @Column
    private Boolean isOnTrial;

    @ManyToMany
    private List<Group> groups;

    // kell-e ManyToOne a group-hoz, employee minőségben???

    @ManyToMany
    private List<Department> departments;

    @Column
    private String position;

    @Column
    private Roles role;

    @Column
    private Integer numberOfChildren;

    @Column
    private String otherAbsenceEnt;

    @OneToMany(mappedBy = "assignee")
    @JsonIgnore
    private List<Absence> absences;

    //?? Kell-e OneToMany List az absences-shez, mint reporter??

}
