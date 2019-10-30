package hu.flowacademy.zetaabsencemanager.model;

import hu.flowacademy.zetaabsencemanager.utils.Constants;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sberned.statemachine.state.HasStateAndId;

@Builder
@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Absence implements HasStateAndId<Long, Status> {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Long id;

  @Column
  private Long administrationID;

  @Column
  @NotNull(message = Constants.TYPE_REQUIRED)
  private Type type;

  @Column
  private String summary;

  @Column
  @NotNull(message = Constants.BEGIN_REQUIRED)
  private LocalDate begin;

  @Column
  @NotNull(message =Constants.END_REQUIRED)
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


  @Override
  public Status getState() {
    return status;
  }
}