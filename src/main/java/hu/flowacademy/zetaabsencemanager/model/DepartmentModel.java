package hu.flowacademy.zetaabsencemanager.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Entity
@Table
public class DepartmentModel {

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
    @OneToMany
    @JsonIgnore
    private Integer groupId;

}
