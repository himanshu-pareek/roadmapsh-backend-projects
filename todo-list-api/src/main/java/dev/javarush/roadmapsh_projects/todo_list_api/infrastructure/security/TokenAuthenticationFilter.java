package dev.javarush.roadmapsh_projects.todo_list_api.infrastructure.security;

import dev.javarush.roadmapsh_projects.todo_list_api.auth.AuthTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private final AuthTokenRepository authTokenRepository;
    private final UserDetailsManager userDetailsManager;

    public TokenAuthenticationFilter(AuthTokenRepository authTokenRepository, UserDetailsManager userDetailsManager) {
        this.authTokenRepository = authTokenRepository;
        this.userDetailsManager = userDetailsManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. Check if this filter can be applied or not
        String authHeader = request.getHeader(AUTH_HEADER);
        if (authHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Check if the header is correct or not
        if (!authHeader.startsWith(TOKEN_PREFIX)) {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        // 3. Validate the token
        String token = authHeader.substring(TOKEN_PREFIX.length());
        Optional<String> usernameForToken = authTokenRepository.findUsernameForToken(token);
        if (usernameForToken.isEmpty()) {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        UserDetails user = userDetailsManager.loadUserByUsername(usernameForToken.get());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        var authenticationToken = new TokenAuthenticationToken(user.getUsername(), true, user.getAuthorities());
        context.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(context);
        filterChain.doFilter(request, response);
    }
}
