package com.behabits.gymbo.domain.exceptions;

public class PermissionsException extends RuntimeException {

    private static final String DESCRIPTION = "Permissions Exception (403)";

    public PermissionsException(String message) {
        super(DESCRIPTION + ". " + message);
    }
}
