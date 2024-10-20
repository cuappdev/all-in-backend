package com.appdev.allin.exceptions;

public class NotForSaleException extends RuntimeException {
    public NotForSaleException(String message) {
        super(message);
    }
}
