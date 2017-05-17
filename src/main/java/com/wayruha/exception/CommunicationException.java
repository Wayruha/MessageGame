package com.wayruha.exception;

/**
 * Unchecked exception that is used to wrap IOExceptions and others that can be thrown during data transporting
 */
public class CommunicationException extends RuntimeException {
    public CommunicationException() {
    }

    public CommunicationException(String message) {
        super(message);
    }
}
