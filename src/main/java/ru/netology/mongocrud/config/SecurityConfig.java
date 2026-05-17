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
                // GET /api/users — публичный endpoint
                .requestMatchers(HttpMethod.GET, "/api/users").permitAll()
                // Все остальные запросы — только авторизованным
                .anyRequest().authenticated()
            )
            // Стандартная форма логина от Spring Security
            .formLogin(form -> form
                .defaultSuccessUrl("/api/users", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/api/users")
                .permitAll()
            )
            // Отключаем CSRF для удобства тестирования REST API через curl/Postman
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    /**
     * In-memory пользователи для демонстрации.
     * В реальном приложении данные берутся из БД.
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("user123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
