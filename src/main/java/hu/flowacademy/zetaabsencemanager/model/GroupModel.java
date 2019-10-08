package hu.flowacademy.zetaabsencemanager.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Builder
@Data
@Entity
@Table
public class GroupModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @Column
    private String name;

    @Column
    @OneToOne
    private Integer leaderId;

    @Column
    @ManyToOne
    @JoinColumn
    private Integer departmentId;

    @Column
    @OneToMany
    @JsonIgnore
    private Integer employeeId;

}


