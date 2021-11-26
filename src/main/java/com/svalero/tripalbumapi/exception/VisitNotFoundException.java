package com.svalero.tripalbumapi.exception;

public class VisitNotFoundException extends Exception {
    private static final String DEFAULT_ERROR_MESSAGE = "Visit not found";

    public VisitNotFoundException(String message) {
        super(message);
    }

    public VisitNotFoundException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
