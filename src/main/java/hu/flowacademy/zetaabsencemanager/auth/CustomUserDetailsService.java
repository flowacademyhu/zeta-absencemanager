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
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;


@Configuration
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        final hu.flowacademy.zetaabsencemanager.model.User user = userRepository.findByEmailAndDeletedAtNull(s).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The submitted arguments are invalid."));

        return new User(s, user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole().toString())));
    }
}
