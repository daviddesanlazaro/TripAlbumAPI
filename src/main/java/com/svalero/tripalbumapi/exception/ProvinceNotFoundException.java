package com.svalero.tripalbumapi.exception;

public class ProvinceNotFoundException extends Exception {
    private static final String DEFAULT_ERROR_MESSAGE = "Province not found";

    public ProvinceNotFoundException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
