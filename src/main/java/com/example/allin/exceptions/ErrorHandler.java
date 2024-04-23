package com.example.allin.exceptions;

import java.util.Map;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public Map<String, String> handleNotFoundException(NotFoundException e) {
    Map<String, String> response = new HashMap<>();
    response.put("error", e.getMessage());
    return response;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(OverdrawnException.class)
  public Map<String, String> handleOverdrawnException(OverdrawnException e) {
    Map<String, String> response = new HashMap<>();
    response.put("error", e.getMessage());
    return response;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(NotForSaleException.class)
  public Map<String, String> handleNotForSaleException(NotForSaleException e) {
    Map<String, String> response = new HashMap<>();
    response.put("error", e.getMessage());
    return response;
  }

  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(ForbiddenException.class)
  public Map<String, String> handleForbiddenException(ForbiddenException e) {
    Map<String, String> response = new HashMap<>();
    response.put("error", e.getMessage());
    return response;
  }

}
