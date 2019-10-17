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
    private Type type;

    @Column
    private Integer summary;

    @Column
    private LocalDate begin;

    @Column
    private LocalDate end;

    @ManyToOne
    private User reporter;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User assignee;

    @Column
    private Status status;

}
