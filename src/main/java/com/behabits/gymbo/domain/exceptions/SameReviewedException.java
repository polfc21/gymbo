package com.behabits.gymbo.domain.exceptions;

public class SameReviewedException extends RuntimeException {

    private static final String DESCRIPTION = "Same Reviewed Exception (400)";

    public SameReviewedException(String message) {
        super(DESCRIPTION + ". " + message);
    }

}
