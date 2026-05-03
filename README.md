# mongo-crud

Spring Boot REST API для управления пользователями. Данные хранятся в MongoDB.

## Стек

- Java 17, Spring Boot 3.2
- Spring Data MongoDB
- Bean Validation, Lombok
- Docker (MongoDB)

## Запуск

```bash
# 1. Поднять MongoDB
docker-compose up -d

# 2. Запустить приложение
mvn spring-boot:run
```

API доступно на `http://localhost:8080`.

## Endpoints

| Метод | URL | Описание |
|-------|-----|----------|
| GET | `/api/users` | Все пользователи |
| GET | `/api/users/{id}` | Пользователь по ID |
| GET | `/api/users/search/by-name?name=` | Поиск по имени |
| GET | `/api/users/search/by-age?age=` | Поиск по возрасту |
| GET | `/api/users/search/by-age-range?minAge=&maxAge=` | Диапазон возраста |
| POST | `/api/users` | Создать пользователя |
| PUT | `/api/users/{id}` | Обновить пользователя |
| DELETE | `/api/users/{id}` | Удалить пользователя |

## Пример запроса

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Иван Иванов","email":"ivan@example.com","age":28}'
```

Ответ `201 Created`:

```json
{
  "id": "665f3a1b2c3d4e5f6a7b8c9d",
  "name": "Иван Иванов",
  "email": "ivan@example.com",
  "age": 28
}
```

## Валидация

- `name` — обязательное, не пустое
- `email` — формат user@domain.tld, уникальный
- `age` — целое число от 0 до 150

Ошибки возвращаются с кодом `400 Bad Request` и описанием по каждому полю.

## Остановить MongoDB

```bash
docker-compose down
```
