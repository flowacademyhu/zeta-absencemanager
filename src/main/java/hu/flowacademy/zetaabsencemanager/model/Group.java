package hu.flowacademy.zetaabsencemanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@Entity
@Table
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @Column
    private String name;

    // ??? ide kell @Column??
    @OneToMany
    @JsonIgnore
    private User leader;
    // private List<User> leaders; ??

    // ??? ide kell @Column??
    @ManyToOne
    @JoinColumn
    private Department department;

    // ??? ide kell @Column??
    @OneToMany
    @JsonIgnore
    private User employee;
    // ???: private List<User> employees;

}


