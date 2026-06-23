package com.sunil.stockportfolio.exceptionhandle;

public class StocksNotFoundException extends RuntimeException {
  public StocksNotFoundException(String message) {
    super(message);
  }
}
