package com.phonebook.service.exceptions;

public class AccountExistException extends RuntimeException {
    public AccountExistException(Throwable cause) {
        super(cause);
    }

    public AccountExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountExistException(String message) {
        super(message);
    }

    public AccountExistException() {
    }
}