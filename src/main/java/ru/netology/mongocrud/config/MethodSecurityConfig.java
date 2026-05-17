package ru.netology.mongocrud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * Конфигурация безопасности на уровне методов.
 *
 * securedEnabled = true  — включает поддержку аннотации @Secured
 * jsr250Enabled  = true  — включает поддержку аннотации @RolesAllowed (JSR-250)
 * prePostEnabled — включён по умолчанию — даёт @PreAuthorize / @PostAuthorize
 */
@Configuration
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true
)
public class MethodSecurityConfig {
}
