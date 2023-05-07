package com.behabits.gymbo.domain.exceptions;

public class LoginException extends RuntimeException {

    private static final String DESCRIPTION = "Login Exception (400)";

    public LoginException(String message) {
        super(DESCRIPTION + ". " + message);
    }

}
