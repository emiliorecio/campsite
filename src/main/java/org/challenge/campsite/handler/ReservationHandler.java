package org.challenge.campsite.handler;

import org.challenge.campsite.exception.EntityNotFound;
import org.challenge.campsite.exception.InvalidRequest;
import org.challenge.campsite.exception.ReservationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.BatchUpdateException;

@ControllerAdvice
public class ReservationHandler extends ResponseEntityExceptionHandler {

    @Value("${campsite.error.db}")
    private String ERROR_DB;


    @ExceptionHandler(InvalidRequest.class)
    public ResponseEntity<?> handleInvalidRequestException(InvalidRequest e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFound e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<?> handleReservationException(ReservationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<?> handleObjectOptimisticLockingFailureException(ObjectOptimisticLockingFailureException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_DB + e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_DB + e.getMessage());
    }

    @ExceptionHandler(BatchUpdateException.class)
    public ResponseEntity<?> handleBatchUpdateException(BatchUpdateException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_DB + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR: " + e.getMessage());
    }
}
