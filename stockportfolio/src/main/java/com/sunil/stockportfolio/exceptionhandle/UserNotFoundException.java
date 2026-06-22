package com.sunil.stockportfolio.exceptionhandle;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
