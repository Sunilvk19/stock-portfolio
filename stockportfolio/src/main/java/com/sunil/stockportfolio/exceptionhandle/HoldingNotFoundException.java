package com.sunil.stockportfolio.exceptionhandle;

public class HoldingNotFoundException extends RuntimeException {
  public HoldingNotFoundException(String message) {
    super(message);
  }
}
