package com.bakafulteam.weplan.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    //handling custom validation errors

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> customValidationErrorHandling(MethodArgumentNotValidException e){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation error", e.getBindingResult().getFieldError().getDefaultMessage() );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
