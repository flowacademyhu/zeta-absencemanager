package hu.flowacademy.zetaabsencemanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Entity
@Table
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column
    private String name;

    @ManyToMany
    @JoinTable(
            name = "user_department",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id"))
    private List<User> leaders;


    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<Group> groups;

}
