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
    private String email;

    @Column
    private String password;

    @Column
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateOfBirth;

    @Column
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateOfEntry;

    @Column
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateOfEndTrial;

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
    private String otherAbsenceEntitlement;

    @Column
    private Integer extraAbsenceDays;

    @Column
    private LocalDateTime extraAbsencesUpdatedAt;

    @OneToMany
    private List<Absence> absences;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

    @ManyToOne
    private User updatedBy;

    @ManyToOne
    private User deletedBy;
}
