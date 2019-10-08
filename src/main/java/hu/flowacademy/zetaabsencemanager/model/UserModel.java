package hu.flowacademy.zetaabsencemanager.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Entity
@Table
public class UserModel {

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

    @Column
    @ManyToOne
    @JoinColumn
    private Integer groupId;

    @Column
    private String position;

    @Column
    private Integer numberOfChildren;

    @Column
    private String otherAbsenceEnt;

    @Column
    @OneToMany
    @JsonIgnore
    private Integer absenceId;

}
