package com.library.api.exception;/*
 * @created 7/28/2021
 *
 * @Author Poran chowdury
 */

public class ResourceExistException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ResourceExistException(String message) {
        super(message);
    }
}
