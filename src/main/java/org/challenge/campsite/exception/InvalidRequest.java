package org.challenge.campsite.exception;

public class InvalidRequest extends RuntimeException {

    public InvalidRequest(String message) {
        super(message);
    }

    public InvalidRequest(String message, Throwable throwable) {
        super(message, throwable);
    }

}
