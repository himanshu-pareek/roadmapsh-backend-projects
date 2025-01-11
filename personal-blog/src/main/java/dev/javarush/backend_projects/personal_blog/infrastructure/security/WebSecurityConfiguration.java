package dev.javarush.backend_projects.personal_blog.infrastructure.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                authz -> authz.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/svg/*").permitAll()
                        .requestMatchers("/assets/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/articles/*").permitAll()
                        .anyRequest().authenticated());
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

}
