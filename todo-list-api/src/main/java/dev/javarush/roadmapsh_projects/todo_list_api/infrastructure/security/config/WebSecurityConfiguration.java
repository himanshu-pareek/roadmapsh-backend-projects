package dev.javarush.roadmapsh_projects.todo_list_api.infrastructure.security.config;

import dev.javarush.roadmapsh_projects.todo_list_api.infrastructure.security.AuthTokenRepository;
import dev.javarush.roadmapsh_projects.todo_list_api.infrastructure.security.web.TokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfiguration {

    private final AuthTokenRepository authTokenRepository;

    public WebSecurityConfiguration(AuthTokenRepository authTokenRepository) {
        this.authTokenRepository = authTokenRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsManager userDetailsManager) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authz ->
                authz.requestMatchers("/auth/*", "/error", "/error/*").permitAll()
                .anyRequest().authenticated());
        http.addFilterBefore(new TokenAuthenticationFilter(authTokenRepository, userDetailsManager), AuthorizationFilter.class);
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }
}
