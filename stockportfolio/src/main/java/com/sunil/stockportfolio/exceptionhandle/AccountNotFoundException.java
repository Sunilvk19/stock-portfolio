package com.sunil.stockportfolio.exceptionhandle;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
