package com.svalero.tripalbumapi.exception;

public class BadRequestException extends Exception {
    private static final String DEFAULT_ERROR_MESSAGE = "Bad Request";

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
