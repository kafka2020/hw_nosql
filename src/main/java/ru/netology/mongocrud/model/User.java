package ru.netology.mongocrud.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Документ MongoDB, представляющий пользователя.
 * Хранится в коллекции "users".
 */
@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /** Уникальный идентификатор документа (генерируется MongoDB автоматически). */
    @Id
    private String id;

    /** Имя пользователя — обязательное поле. */
    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    /** Email — обязательный, уникальный, с проверкой формата. */
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный формат email")
    @Indexed(unique = true)
    private String email;

    /** Возраст — число от 0 до 150. */
    @NotNull(message = "Возраст обязателен")
    @Min(value = 0, message = "Возраст не может быть отрицательным")
    @Max(value = 150, message = "Возраст не может превышать 150")
    private Integer age;
}
