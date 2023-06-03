package org.geekhub.yurii.service.user;

import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.dto.user.UserAuthDTO;
import org.geekhub.yurii.exception.UsernameIsNotAvailableException;
import org.geekhub.yurii.model.user.Role;
import org.geekhub.yurii.model.user.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void signUp(UserAuthDTO authDTO) {
        if (!isAvailableUsername(authDTO)) {
            throw new UsernameIsNotAvailableException("Username is not available");
        }

        save(authDTO);
        authenticate(authDTO);
    }

    private boolean isAvailableUsername(UserAuthDTO authDTO) {
        if (authDTO.getUsername().equals("anonymousUser")) return false;
        return userService.findByUsername(authDTO.getUsername()).isEmpty();
    }

    private void save(UserAuthDTO authDTO) {
        User user = new User();

        user.setUsername(authDTO.getUsername());
        user.setEmail(authDTO.getEmail());
        user.setPassword(passwordEncoder.encode(authDTO.getPassword()));
        user.setRole(Role.ROLE_USER);

        userService.save(user);
    }

    private void authenticate(UserAuthDTO authDTO) {
        UsernamePasswordAuthenticationToken authReq =
                new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword());
        Authentication auth = authenticationManager.authenticate(authReq);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public boolean isOwnPage(String username) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return auth.getName().equals(username);
    }

    public boolean isAdmin() {
        return hasRole(Role.ROLE_ADMIN);
    }

    public boolean isUser() {
        return hasRole(Role.ROLE_USER);
    }

    private boolean hasRole(Role role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return false;

        return auth.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(role.toString()));
    }

    public String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return auth.getName();
    }
}
