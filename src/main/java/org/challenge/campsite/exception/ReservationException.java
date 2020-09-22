package org.challenge.campsite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ReservationException extends RuntimeException{

	public ReservationException(String message) {
		super(message);
	}

}
