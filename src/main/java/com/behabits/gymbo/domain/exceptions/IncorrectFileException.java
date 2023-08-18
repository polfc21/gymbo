package com.behabits.gymbo.domain.exceptions;

public class IncorrectFileException extends RuntimeException {

    private static final String DESCRIPTION = "Incorrect File Exception (400)";

    public IncorrectFileException(String message) {
        super(DESCRIPTION + ". " + message);
    }

}
