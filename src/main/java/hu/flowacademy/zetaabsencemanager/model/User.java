package hu.flowacademy.zetaabsencemanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import hu.flowacademy.zetaabsencemanager.model.serializer.GroupSerializer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Long id;

  @NotBlank(message = "Firstname is required.")
  @Column
  private String firstName;

  @NotBlank(message = "Lastname is required.")
  @Column
  private String lastName;

  @NotBlank(message = "Email is required.")
  @Column(unique = true)
  private String email;

  @Column
  private String password;

  @Column
  @Past
  @NotNull(message = "Date of birth is required.")
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate dateOfBirth;

  @Column
  @NotNull(message = "Date of entry is required.")
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate dateOfEntry;

  @Column
  @NotNull(message = "Date of trial end is required.")
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate dateOfEndTrial;

  @ManyToOne
  @JsonSerialize(using = GroupSerializer.class)
  private Group group;

  @Column
  @NotBlank(message = "Position is required.")
  private String position;

  @Column
  private Roles role;

  @Column
  @NotNull(message = "Number of children is required.")
  private Integer numberOfChildren;

  @Column
  private String otherAbsenceEntitlement;

  @Column
  private Integer totalAbsenceDays;

  @Column
  private Integer usedAbsenceDays;

  @Column
  private Integer totalSickLeaveDays;

  @Column
  private Integer usedSickLeaveDays;

  @Column
  private Integer usedSickPay;

  @Column
  private Integer childSickPay;

  @Column
  private Integer extraAbsenceDays;

  @Column
  private LocalDateTime extraAbsencesUpdatedAt;

  @OneToMany
  private List<Absence> absences;

  @Column
  private LocalDateTime createdAt;

  @Column
  private LocalDateTime updatedAt;

  @Column
  private LocalDateTime deletedAt;

  @ManyToOne
  private User updatedBy;

  @ManyToOne
  private User deletedBy;

  @JsonIgnore
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(this.role.name()));
  }

  @Override
  public String getUsername() {
    return this.getEmail();
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isEnabled() {
    return true;
  }

  @JsonIgnore
  public String getPassword() {
    return password;
  }

  @JsonProperty
  public void setPassword(String password) {
    this.password = password;
  }
}