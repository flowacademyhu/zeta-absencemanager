package hu.flowacademy.zetaabsencemanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Absence {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

    @Column
    private Type type;

    @Column
    private String summary;

    @Column
    private LocalDate begin;

    @Column
    private LocalDate end;

    @ManyToOne
    private User createdBy;

    // TODO User updatedBy;

    // TODO User deletedBy

    @ManyToOne
    private User reporter;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User assignee;

    @Column
    private Status status;

}
