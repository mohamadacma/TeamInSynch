package com.nashss.se.teaminsynchservice.exceptions;

/**
 * Exception to throw when a given memberId is not found
 * in the database.
 */

public class MemberNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -8488255386633342056L;

    /**
     * Exception with no message or cause.
     */
    public MemberNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public MemberNotFoundException(String message) { super(message); }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public MemberNotFoundException(Throwable cause) {super(cause); }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public  MemberNotFoundException(String message, Throwable cause) {super(message, cause); }
}
