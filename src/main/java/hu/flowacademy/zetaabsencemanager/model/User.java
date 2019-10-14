package hu.flowacademy.zetaabsencemanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
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

    @Column
    private LocalDateTime deletedAt;

}
