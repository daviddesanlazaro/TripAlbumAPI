package com.svalero.tripalbumapi.exception;

public class InternalServerErrorException extends Exception {
    private static final String DEFAULT_ERROR_MESSAGE = "Internal Server Error";

    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
