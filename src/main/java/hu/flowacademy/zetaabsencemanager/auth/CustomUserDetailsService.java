package hu.flowacademy.zetaabsencemanager.auth;

import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.repository.UserRepository;
import hu.flowacademy.zetaabsencemanager.utils.Constants;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;


@Configuration
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public User loadUserByUsername(String s) throws UsernameNotFoundException {
    return userRepository.findByEmailAndDeletedAtNull(s).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
            Constants.INVALID_ARGUMENTS));
  }
}
