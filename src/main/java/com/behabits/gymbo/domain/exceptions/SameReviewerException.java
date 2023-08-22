package com.behabits.gymbo.domain.exceptions;

public class SameReviewerException extends RuntimeException {

    private static final String DESCRIPTION = "Same Reviewer Exception (400)";

    public SameReviewerException(String message) {
        super(DESCRIPTION + ". " + message);
    }

}
