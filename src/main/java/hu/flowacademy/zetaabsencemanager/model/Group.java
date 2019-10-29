package hu.flowacademy.zetaabsencemanager.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import hu.flowacademy.zetaabsencemanager.model.serializer.UserSerializer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  @Column(unique = true)
  @NotBlank(message = "Group name is required.")
  private String name;

  @OneToOne
  private User leader;

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