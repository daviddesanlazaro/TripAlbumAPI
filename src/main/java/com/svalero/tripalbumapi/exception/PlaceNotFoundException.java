package com.svalero.tripalbumapi.exception;

public class PlaceNotFoundException extends Exception {
    private static final String DEFAULT_ERROR_MESSAGE = "Place not found";

    public PlaceNotFoundException(String message) {
        super(message);
    }

    public PlaceNotFoundException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
