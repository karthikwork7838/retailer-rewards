package com.retailer.rewards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * Global exception handler for the
 * retailer rewards application.
 *
 * This class centralizes exception
 * handling across all REST controllers
 * and provides consistent error
 * responses to API consumers.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * This method handles the exception thrown whe customer not being found for the respective ID
     * @param ex CustomerNotFoundException
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFound(CustomerNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }
}