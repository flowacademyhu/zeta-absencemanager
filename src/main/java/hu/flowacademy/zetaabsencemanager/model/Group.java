package hu.flowacademy.zetaabsencemanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import hu.flowacademy.zetaabsencemanager.model.serializer.UserSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @JsonSerialize(using = UserSerializer.class)
    private List<User> leaders = new ArrayList<>();


    @OneToMany(mappedBy = "group")
    @JsonSerialize(using = UserSerializer.class)
    private List<User> employees = new ArrayList<>();

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

}


