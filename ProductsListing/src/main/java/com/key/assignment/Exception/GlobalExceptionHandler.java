package com.key.assignment.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MyErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest req) {
    	MyErrorDetails errorResponse = new MyErrorDetails(LocalDateTime.now(), ex.getMessage(),req.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<MyErrorDetails> handleCategoryAlreadyExistsException(CategoryAlreadyExistsException ex, WebRequest req) {
    	MyErrorDetails errorResponse = new MyErrorDetails(LocalDateTime.now(), ex.getMessage(),req.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
