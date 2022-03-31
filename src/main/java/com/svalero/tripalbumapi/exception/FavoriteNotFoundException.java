package com.svalero.tripalbumapi.exception;

public class FavoriteNotFoundException extends Exception {
    private static final String DEFAULT_ERROR_MESSAGE = "Favorite not found";

    public FavoriteNotFoundException(String message) {
        super(message);
    }

    public FavoriteNotFoundException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
