package dev.javarush.roadmapsh_projects.todo_list_api.auth.web;

import dev.javarush.roadmapsh_projects.todo_list_api.auth.AuthToken;
import dev.javarush.roadmapsh_projects.todo_list_api.auth.AuthTokenRepository;
import dev.javarush.roadmapsh_projects.todo_list_api.auth.InvalidCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final AuthTokenRepository authTokenRepository;

    public AuthController(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder, AuthTokenRepository authTokenRepository) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
        this.authTokenRepository = authTokenRepository;
    }

    @PostMapping("login")
    public AuthToken login(@RequestBody @Validated LoginRequestPayload data) {
        String username = data.email().trim().toLowerCase();
        String password = data.password();
        try {
            UserDetails userInDb = userDetailsManager.loadUserByUsername(username);
            if (passwordEncoder.matches(password, userInDb.getPassword())) {
                return authTokenRepository.createAndSaveToken(username);
            }
            throw new InvalidCredentialsException("Invalid username or password");
        } catch (UsernameNotFoundException ex) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("register")
    public String register(@RequestBody @Validated RegisterRequestPayload data) {
        String username = data.email().trim().toLowerCase();
        String password = data.password();
        if (userDetailsManager.userExists(username)) {
            return "User with email '" + username + "' already exists";
        }
        UserDetails user = User.withUsername(username).password(passwordEncoder.encode(password)).roles("USER").build();
        userDetailsManager.createUser(user);
        return "🎉 User created successfully - " + user.getUsername();
    }
}
