package hu.flowacademy.zetaabsencemanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
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
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateOfBirth;

    @Column
    private String email;

    @Column
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateOfEntry;

    @Column
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateOfEndTrial;

    @Column
    private Boolean isOnTrial;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Column
    private String position;

    @Column
    private Roles role;

    @Column
    private Integer numberOfChildren;

    @Column
    private String otherAbsenceEnt;

    @OneToMany(mappedBy = "reporter")
    @JsonIgnore
    private List<Absence> absences;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

    // TODO User updatedBy;

    // TODO User deletedBy

}
