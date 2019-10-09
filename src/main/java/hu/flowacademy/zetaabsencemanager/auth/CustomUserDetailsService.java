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
        if (user == null) {
            throw new UsernameNotFoundException(s);
        }
        return new User(s, user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole().toString())));
    }

    public void saveUser(hu.flowacademy.zetaabsencemanager.model.User user) {
        hu.flowacademy.zetaabsencemanager.model.User newUser = new hu.flowacademy.zetaabsencemanager.model.User();
        if (user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("nincs pw vagy username");
        }
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setDateOfBirth(user.getDateOfBirth());
        newUser.setDateOfEntry(user.getDateOfEntry());
        newUser.setDateOfEndTrial(user.getDateOfEndTrial());
        newUser.setEmail(user.getEmail());
        newUser.setGroups(user.getGroups());
        //Departments according to the groups?
        newUser.setPosition(user.getPosition());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());
        newUser.setNumberOfChildren(user.getNumberOfChildren());
        newUser.setOtherAbsenceEnt(user.getOtherAbsenceEnt());
        userRepository.save(newUser);
    }
}
