# Домашнее задание — Spring Security: Безопасное приложение

## Описание

Проект представляет собой REST API для управления пользователями на базе **Spring Boot** и **MongoDB**.
В данном домашнем задании к приложению добавлена защита с помощью **Spring Security**.

## Что реализовано

- Добавлена зависимость `spring-boot-starter-security` в `pom.xml`
- Реализован класс конфигурации `SecurityConfig` с использованием `SecurityFilterChain`
- Настроена стандартная форма логина Spring Security (`/login`)
- Разграничен доступ к endpoints:

| Endpoint                         | Метод  | Доступ                      |
|----------------------------------|--------|-----------------------------|
| `/api/users`                     | GET    | Публичный (без авторизации) |
| `/api/users/{id}`                | GET    | Только авторизованным       |
| `/api/users/search/by-name`      | GET    | Только авторизованным       |
| `/api/users/search/by-age`       | GET    | Только авторизованным       |
| `/api/users/search/by-age-range` | GET    | Только авторизованным       |
| `/api/users`                     | POST   | Только авторизованным       |
| `/api/users/{id}`                | PUT    | Только авторизованным       |
| `/api/users/{id}`                | DELETE | Только авторизованным       |

## Тестовые пользователи (In-Memory)

| Логин   | Пароль     | Роль  |
|---------|------------|-------|
| `admin` | `admin123` | ADMIN |
| `user`  | `user123`  | USER  |

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

## Проверка безопасности

### Публичный endpoint (без авторизации)

```bash
# Доступно без логина — вернёт список всех пользователей
curl http://localhost:8080/api/users
```

### Защищённый endpoint (требует авторизации)

```bash
# Без авторизации — редирект на страницу /login
curl -v http://localhost:8080/api/users/123

# С авторизацией (Basic Auth) — вернёт данные пользователя
curl -u admin:admin123 http://localhost:8080/api/users/123
```

### Форма логина в браузере

Откройте: `http://localhost:8080/login`

Введите логин и пароль из таблицы тестовых пользователей выше.

## Технологии

- Java 17
- Spring Boot 3.2
- Spring Security 6
- Spring Data MongoDB
- MongoDB
- Lombok
- Docker / Docker Compose
