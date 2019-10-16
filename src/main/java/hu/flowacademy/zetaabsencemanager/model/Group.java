package hu.flowacademy.zetaabsencemanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@Entity
@Table(name = "_group")
@AllArgsConstructor
@NoArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column
    private Long parentId;

    @Column
    private String name;

    @ManyToMany
    @JoinTable(
            name = "leader_user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<User> leaders;


    @OneToMany(mappedBy = "group")
    @JsonIgnore
    private List<User> employees;

    @Column
    private LocalDateTime deletedAt;

}


