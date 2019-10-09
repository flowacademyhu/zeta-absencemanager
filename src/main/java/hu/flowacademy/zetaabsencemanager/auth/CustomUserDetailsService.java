package hu.flowacademy.zetaabsencemanager.auth;

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
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole();
        userRepository.save(newUser);
    }
}
