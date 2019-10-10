package hu.flowacademy.zetaabsencemanager.auth;

import hu.flowacademy.zetaabsencemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;


@Configuration
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        final hu.flowacademy.zetaabsencemanager.model.User user = userRepository.findByEmail(s);
        if (user == null) {
            throw new UsernameNotFoundException(s);
        }
        return new User(s, user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole().toString())));
    }

    public hu.flowacademy.zetaabsencemanager.model.User saveUser(@NotNull hu.flowacademy.zetaabsencemanager.model.User user) {
        if (StringUtils.isEmpty(user.getFirstName())
                || StringUtils.isEmpty(user.getLastName())
                || user.getDateOfBirth() == null
                || StringUtils.isEmpty(user.getEmail())
                || user.getDateOfEntry() == null
                || user.getDateOfEndTrial() == null
                || user.getIsOnTrial() == null
                || CollectionUtils.isEmpty(user.getDepartments())
                || CollectionUtils.isEmpty(user.getGroups())
                || StringUtils.isEmpty(user.getPosition())
                || user.getRole() == null
                || user.getNumberOfChildren() == null
                || user.getOtherAbsenceEnt() == null
        ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The submitted arguments are invalid.");
        } else {
            hu.flowacademy.zetaabsencemanager.model.User newUser = hu.flowacademy.zetaabsencemanager.model.User.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .dateOfBirth(user.getDateOfBirth())
                    .dateOfEntry(user.getDateOfEntry())
                    .dateOfEndTrial(user.getDateOfEndTrial())
                    .isOnTrial(user.getIsOnTrial())
                    .email(user.getEmail())
                    .groups(user.getGroups())
                    .departments(user.getDepartments())
                    .position(user.getPosition())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .role(user.getRole())
                    .numberOfChildren(user.getNumberOfChildren())
                    .otherAbsenceEnt(user.getOtherAbsenceEnt())
                    .build();
            userRepository.save(newUser);
            return newUser;
        }
    }
}
