/*package hu.flowacademy.zetaabsencemanager.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import javax.transaction.Transactional;

@Configuration
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException(s);
        }
        System.out.println(user.getUsername() + "load");
        return new User(s, user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole())));
    }

    public void saveUser(User user) {
        User newUser = new User();
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("nincs pw vagy username");
        }
        user.setUsername(user.getUsername());
        System.out.println("saveP" + user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
    }
}*/
