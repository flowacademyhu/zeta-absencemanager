package hu.flowacademy.zetaabsencemanager.service;

import hu.flowacademy.zetaabsencemanager.model.Roles;
import hu.flowacademy.zetaabsencemanager.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Transactional
public class AuthenticationService {

    public User getCurrentUser() {
/*        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "user not found");
        }
        String email = auth.getName();
        return findByEmail(email);*/
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)  
                .map(this::castUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
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
