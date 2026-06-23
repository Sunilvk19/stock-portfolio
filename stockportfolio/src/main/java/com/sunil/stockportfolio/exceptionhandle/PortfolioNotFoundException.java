package com.sunil.stockportfolio.exceptionhandle;

public class PortfolioNotFoundException extends RuntimeException {
  public PortfolioNotFoundException(String message) {
    super(message);
  }
}
