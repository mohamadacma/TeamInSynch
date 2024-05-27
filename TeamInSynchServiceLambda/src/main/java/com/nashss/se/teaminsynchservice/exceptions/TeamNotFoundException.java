package com.nashss.se.teaminsynchservice.exceptions;

public class TeamNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 3615165355008335611L;

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public TeamNotFoundException(String message) { super(message); }
}
