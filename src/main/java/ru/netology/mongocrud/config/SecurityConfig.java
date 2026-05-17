package ru.netology.mongocrud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Настройка правил доступа:
     * - GET /api/users          — публичный (без авторизации)
     * - Все остальные запросы   — только для авторизованных пользователей
     * - Вход через стандартную форму логина Spring Security
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/api/users").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .defaultSuccessUrl("/api/users", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/api/users")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    /**
     * In-memory пользователи с разными наборами ролей.
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails reader = User.builder()
                .username("reader")
                .password(passwordEncoder().encode("reader123"))
                .roles("READ")
                .build();

        UserDetails writer = User.builder()
                .username("writer")
                .password(passwordEncoder().encode("writer123"))
                .roles("WRITE")
                .build();

        UserDetails deleter = User.builder()
                .username("deleter")
                .password(passwordEncoder().encode("deleter123"))
                .roles("DELETE")
                .build();

        UserDetails superuser = User.builder()
                .username("superuser")
                .password(passwordEncoder().encode("super123"))
                .roles("READ", "WRITE", "DELETE")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN", "READ", "WRITE", "DELETE")
                .build();

        return new InMemoryUserDetailsManager(reader, writer, deleter, superuser, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
