package dev.javarush.roadmapsh.image_processing.api.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable);
    http.httpBasic(Customizer.withDefaults());
    http.authorizeHttpRequests(
        authz -> authz
            .anyRequest().authenticated()
    );
    return http.build();
  }

  @Bean
  UserDetailsService userDetailsService() {
    return new InMemoryUserDetailsManager();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  @Profile("init")
  CommandLineRunner initUsers(
      UserDetailsService userDetailsService,
      PasswordEncoder passwordEncoder
  ) {
    return args -> {
      var userDetailsManager = (UserDetailsManager) userDetailsService;
      if (!userDetailsManager.userExists("bob")) {
        var user = org.springframework.security.core.userdetails.User
            .withUsername("bob")
            .password(passwordEncoder.encode("password"))
            .roles("USER")
            .build();
        userDetailsManager.createUser(user);
      }
      if (!userDetailsManager.userExists("alice")) {
        var user = org.springframework.security.core.userdetails.User
            .withUsername("alice")
            .password(passwordEncoder.encode("password"))
            .roles("USER", "ADMIN")
            .build();
        userDetailsManager.createUser(user);
      }
      if (!userDetailsManager.userExists("system")) {
        var user = org.springframework.security.core.userdetails.User
            .withUsername("system")
            .password(passwordEncoder.encode("password"))
            .roles("SYSTEM")
            .build();
        userDetailsManager.createUser(user);
      }
    };
  }
}
