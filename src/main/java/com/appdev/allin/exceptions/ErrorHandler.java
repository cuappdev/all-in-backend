package com.appdev.allin.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    public Map<String, String> createErrorResponse(Exception e) {
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getMessage());
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        logger.error("Exception occurred: {}", e.getMessage());
        return response;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public Map<String, String> handleNotFoundException(NotFoundException e) {
        return createErrorResponse(e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OverdrawnException.class)
    public Map<String, String> handleOverdrawnException(OverdrawnException e) {
        return createErrorResponse(e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotForSaleException.class)
    public Map<String, String> handleNotForSaleException(NotForSaleException e) {
        return createErrorResponse(e);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public Map<String, String> handleForbiddenException(ForbiddenException e) {
        return createErrorResponse(e);
    }
}
