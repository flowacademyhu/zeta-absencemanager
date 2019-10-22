package hu.flowacademy.zetaabsencemanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateOfBirth;

    @Column
    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateOfEntry;

    @Column
    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateOfEndTrial;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @NotBlank
    @Column
    private String position;

    @Column
    private Roles role;

    @NotNull
    @Column
    private Integer numberOfChildren;

    @Column
    private String otherAbsenceEntitlement;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(this.getRole().name()));
        return authorities;
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
