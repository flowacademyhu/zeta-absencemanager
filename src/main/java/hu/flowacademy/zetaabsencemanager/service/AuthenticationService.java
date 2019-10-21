package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.User;
import hu.flowacademy.zetaabsencemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmailAndDeletedAtNull(principal.toString()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

/*        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)  
                .map(this::castUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));*/
    }

    private User castUser(Object principal) {
        if (principal instanceof User) {
            return (User) principal;
        }
        return null;
    }

    public Boolean hasRole(Roles role) {
        return getCurrentUser().getAuthorities().stream().anyMatch(u -> u.getAuthority().equalsIgnoreCase(role.toString()));
    }
}
