# Домашнее задание — Spring Method Security: Безопасные методы

## Описание

Проект представляет собой REST API для управления пользователями на базе **Spring Boot** и **MongoDB**.
В данном ДЗ добавлена безопасность на уровне методов (Method Security)
поверх Spring Security из предыдущего задания.

## Что реализовано

### Spring Security (из предыдущего ДЗ)
- Форма логина Spring Security (`/login`)
- `GET /api/users` — публичный endpoint (без авторизации)
- Все остальные endpoints — только после авторизации

### Method Security (новое)
- Включены все три типа аннотаций: `@Secured`, `@RolesAllowed`, `@PreAuthorize` / `@PostAuthorize`
- Добавлен новый контроллер `SecureController` (`/api/secure/**`)
- Добавлены пользователи с ролями READ / WRITE / DELETE

## Пользователи

| Логин       | Пароль       | Роли                        |
|------------|-------------|----------------------------|
| `reader`   | `reader123` | READ                       |
| `writer`   | `writer123` | WRITE                      |
| `deleter`  | `deleter123`| DELETE                     |
| `superuser`| `super123`  | READ, WRITE, DELETE        |
| `admin`    | `admin123`  | ADMIN, READ, WRITE, DELETE |

## Endpoints контроллера /api/secure

| Endpoint                      | Метод | Аннотация       | Кто имеет доступ                     |
|-------------------------------|-------|-----------------|--------------------------------------|
| `/api/secure/read`            | GET   | `@Secured`      | Роль READ                           |
| `/api/secure/write`           | GET   | `@RolesAllowed` | Роль WRITE                          |
| `/api/secure/write-or-delete` | GET   | `@PreAuthorize` | Роль WRITE **или** DELETE            |
| `/api/secure/my-data`         | GET   | `@PreAuthorize` | Только сам пользователь (by username) |

## Запуск приложения

### Предварительные требования

- Java 17+
- Maven 3.8+
- Docker и Docker Compose (для MongoDB)

### 1. Запустить MongoDB

```bash
docker-compose up -d
```

### 2. Запустить приложение

```bash
mvn spring-boot:run
```

Приложение будет доступно по адресу: `http://localhost:8080`

## Примеры запросов

```bash
# Доступно для reader и superuser
curl -u reader:reader123 http://localhost:8080/api/secure/read

# Доступно для writer и superuser
curl -u writer:writer123 http://localhost:8080/api/secure/write

# Доступно для writer, deleter и superuser
curl -u deleter:deleter123 http://localhost:8080/api/secure/write-or-delete

# Доступно: username совпадает с логином
curl -u reader:reader123 "http://localhost:8080/api/secure/my-data?username=reader"

# Ошибка 403: reader пытается получить данные чужого пользователя
curl -u reader:reader123 "http://localhost:8080/api/secure/my-data?username=writer"
```

## Технологии

- Java 17
- Spring Boot 3.2
- Spring Security 6 (Method Security)
- Spring Data MongoDB
- Lombok
- Docker / Docker Compose
