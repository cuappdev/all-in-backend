package com.example.allin.exceptions;

public class NotForSaleException extends RuntimeException {
  public NotForSaleException(String message) {
    super(message);
  }
}
