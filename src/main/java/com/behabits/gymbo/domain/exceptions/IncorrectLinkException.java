package com.behabits.gymbo.domain.exceptions;

public class IncorrectLinkException extends RuntimeException {

    private static final String DESCRIPTION = "Incorrect Link Exception (400)";

    public IncorrectLinkException(String message) {
        super(DESCRIPTION + ". " + message);
    }

}
