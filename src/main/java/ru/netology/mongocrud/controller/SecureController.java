package ru.netology.mongocrud.controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для демонстрации безопасности на уровне методов.
 * Базовый URL: /api/secure
 */
@RestController
@RequestMapping("/api/secure")
public class SecureController {

    /**
     * GET /api/secure/read
     * Только для пользователей с ролью ROLE_READ.
     * Используется аннотация @Secured.
     */
    @GetMapping("/read")
    @Secured("ROLE_READ")
    public String readData() {
        return "Данные доступны: у вас есть роль READ";
    }

    /**
     * GET /api/secure/write
     * Только для пользователей с ролью ROLE_WRITE.
     * Используется аннотация @RolesAllowed (JSR-250).
     */
    @GetMapping("/write")
    @RolesAllowed("ROLE_WRITE")
    public String writeData() {
        return "Операция разрешена: у вас есть роль WRITE";
    }

    /**
     * GET /api/secure/write-or-delete
     * Доступен для пользователей с ролью WRITE или DELETE.
     * Используется @PreAuthorize.
     */
    @GetMapping("/write-or-delete")
    @PreAuthorize("hasRole('WRITE') or hasRole('DELETE')")
    public String writeOrDeleteData() {
        return "Доступ разрешён: у вас есть роль WRITE или DELETE";
    }

    /**
     * GET /api/secure/my-data?username=reader
     * Возвращает данные только если query-параметр username совпадает
     * с именем аутентифицированного пользователя в SecurityContextHolder.
     * Используется @PreAuthorize с выражением authentication.name.
     */
    @GetMapping("/my-data")
    @PreAuthorize("#username == authentication.name")
    public String getMyData(@RequestParam String username) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "Привет, " + auth.getName() + "! Ваши личные данные получены успешно.";
    }
}
