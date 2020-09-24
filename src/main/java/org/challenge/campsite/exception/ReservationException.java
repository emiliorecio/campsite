package org.challenge.campsite.exception;

public class ReservationException extends RuntimeException {

    public ReservationException(String message) {
        super(message);
    }

    public ReservationException(String message, Throwable throwable) {
        super(message, throwable);
    }


}
