package com.appdev.allin.exceptions;

public class OverdrawnException extends RuntimeException {
  public OverdrawnException(String message) {
    super(message);
  }
}
