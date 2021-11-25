package com.svalero.tripalbumapi.exception;

public class CountryNotFoundException extends Exception {
    private static final String DEFAULT_ERROR_MESSAGE = "Country not found";

    public CountryNotFoundException(String message) {
        super(message);
    }

    public CountryNotFoundException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

}
