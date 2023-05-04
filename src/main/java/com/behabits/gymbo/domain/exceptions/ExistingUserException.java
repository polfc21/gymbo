package com.behabits.gymbo.domain.exceptions;

public class ExistingUserException extends RuntimeException {

    private static final String DESCRIPTION = "Existing User Exception (409)";

    public ExistingUserException(String message) {
        super(DESCRIPTION + ". " + message);
    }
}
