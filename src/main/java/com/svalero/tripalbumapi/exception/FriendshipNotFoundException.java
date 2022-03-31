package com.svalero.tripalbumapi.exception;

public class FriendshipNotFoundException extends Exception {
    private static final String DEFAULT_ERROR_MESSAGE = "Friendship not found";

    public FriendshipNotFoundException(String message) {
        super(message);
    }

    public FriendshipNotFoundException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
