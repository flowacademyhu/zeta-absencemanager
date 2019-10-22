package hu.flowacademy.zetaabsencemanager.model;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

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
    @NotNull
    private Type type;

    @Column
    private String summary;

    @Column
    @NotNull
    private LocalDate begin;

    @Column
    @NotNull
    private LocalDate end;

    @Column
    private Integer duration;

    @ManyToOne
    private User reporter;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User assignee;

    @Column
    private Status status;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    private User updatedBy;

    @ManyToOne
    private User deletedBy;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;


}
