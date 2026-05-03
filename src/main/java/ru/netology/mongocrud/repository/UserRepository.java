package ru.netology.mongocrud.repository;

import ru.netology.mongocrud.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для CRUD-операций с коллекцией "users".
 * Spring Data MongoDB автоматически реализует все методы
 * по их именам (Query Method derivation).
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /** Поиск пользователей по имени (регистр не важен). */
    List<User> findByNameIgnoreCase(String name);

    /** Поиск всех пользователей указанного возраста. */
    List<User> findByAge(Integer age);

    /** Поиск пользователей в заданном диапазоне возрастов. */
    List<User> findByAgeBetween(Integer minAge, Integer maxAge);

    /** Поиск по email (регистр не важен). */
    Optional<User> findByEmailIgnoreCase(String email);

    /** Проверка: существует ли пользователь с таким email. */
    boolean existsByEmailIgnoreCase(String email);
}
