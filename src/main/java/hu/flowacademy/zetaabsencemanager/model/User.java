package hu.flowacademy.zetaabsencemanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String password;

    @Column
    private LocalDate dateOfBirth;

    @Column
    private String email;

    @Column
    private LocalDate dateOfEntry;

    @Column
    private LocalDate dateOfEndTrial;

    @Column
    private Boolean isOnTrial;

    @ManyToMany
    private List<Group> groups;

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

}
