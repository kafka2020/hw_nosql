package ru.netology.mongocrud.controller;

import ru.netology.mongocrud.exception.UserNotFoundException;
import ru.netology.mongocrud.model.User;
import ru.netology.mongocrud.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления пользователями.
 * Базовый URL: /api/users
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    // ===================== GET =====================

    /** GET /api/users — получить всех пользователей. */
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /** GET /api/users/{id} — получить пользователя по ID. */
    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /** GET /api/users/search/by-name?name=Иван — поиск по имени. */
    @GetMapping("/search/by-name")
    public List<User> getUsersByName(@RequestParam String name) {
        return userRepository.findByNameIgnoreCase(name);
    }

    /** GET /api/users/search/by-age?age=25 — поиск по возрасту. */
    @GetMapping("/search/by-age")
    public List<User> getUsersByAge(@RequestParam Integer age) {
        return userRepository.findByAge(age);
    }

    /**
     * GET /api/users/search/by-age-range?minAge=18&maxAge=30
     * Поиск пользователей в диапазоне возрастов.
     */
    @GetMapping("/search/by-age-range")
    public List<User> getUsersByAgeRange(
            @RequestParam Integer minAge,
            @RequestParam Integer maxAge) {
        return userRepository.findByAgeBetween(minAge, maxAge);
    }

    // ===================== POST =====================

    /**
     * POST /api/users — создать нового пользователя.
     * Тело запроса: {"name":"Иван","email":"ivan@mail.ru","age":25}
     * Возвращает: 201 Created + созданный объект с присвоенным id.
     */
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            throw new IllegalArgumentException(
                    "Пользователь с email '" + user.getEmail() + "' уже существует");
        }
        user.setId(null); // MongoDB сам сгенерирует id
        User saved = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // ===================== PUT =====================

    /**
     * PUT /api/users/{id} — полное обновление пользователя.
     * Тело запроса: {"name":"Новое имя","email":"new@mail.ru","age":30}
     */
    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @Valid @RequestBody User updatedUser) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // Проверяем уникальность email, если он изменился
        if (!existing.getEmail().equalsIgnoreCase(updatedUser.getEmail())
                && userRepository.existsByEmailIgnoreCase(updatedUser.getEmail())) {
            throw new IllegalArgumentException(
                    "Email '" + updatedUser.getEmail() + "' уже занят другим пользователем");
        }

        existing.setName(updatedUser.getName());
        existing.setEmail(updatedUser.getEmail());
        existing.setAge(updatedUser.getAge());
        return userRepository.save(existing);
    }

    // ===================== DELETE =====================

    /**
     * DELETE /api/users/{id} — удалить пользователя по ID.
     * Возвращает: 204 No Content при успехе.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
