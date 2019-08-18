package com.hclc.nrgyinvoicr.backend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.UUID;
import java.util.logging.Logger;

import static java.util.logging.Level.FINE;
import static java.util.logging.Level.SEVERE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = Logger.getLogger(ExceptionsHandler.class.getName());

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        String exceptionTrackingId = UUID.randomUUID().toString().substring(0, 6);
        logger.log(SEVERE, "[Exception tracking id: " + exceptionTrackingId + "]", e);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ErrorResponse("Unknown server error. Please contact system administrator. [" + exceptionTrackingId + "]"));
    }

    @ExceptionHandler({EntityNotFoundException.class})
    protected ResponseEntity<Void> handleException(EntityNotFoundException e) {
        logger.log(FINE, e.getMessage());
        return ResponseEntity.notFound().build();
    }
}
