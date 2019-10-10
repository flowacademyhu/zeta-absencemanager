package hu.flowacademy.zetaabsencemanager.auth;

import hu.flowacademy.zetaabsencemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;
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
        System.out.println(s);
        if (user == null) {
            throw new UsernameNotFoundException(s);
        }
        return new User(s, user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole().toString())));
    }

    public void saveUser(hu.flowacademy.zetaabsencemanager.model.User user) {
        if (user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("nincs pw vagy username");
        }
        hu.flowacademy.zetaabsencemanager.model.User newUser = hu.flowacademy.zetaabsencemanager.model.User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .dateOfEntry(user.getDateOfEntry())
                .dateOfEndTrial(user.getDateOfEndTrial())
                .email(user.getEmail())
                .groups(user.getGroups())
                //Departments according to the groups?
                .position(user.getPosition())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(user.getRole())
                .numberOfChildren(user.getNumberOfChildren())
                .otherAbsenceEnt(user.getOtherAbsenceEnt())
                .build();
        userRepository.save(newUser);
    }
}
