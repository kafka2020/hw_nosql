package com.example.mongocrud.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String id) {
        super("Пользователь с id '" + id + "' не найден");
    }
}
