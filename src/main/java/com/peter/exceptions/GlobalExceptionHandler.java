package com.peter.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.List;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(DroneException.class)
    public ResponseEntity<ErrorResponse> handleDroneException(DroneException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), "System Rule Violation", ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(ex.getErrorCode()));
    }

    @ExceptionHandler(MedicationException.class)
    public ResponseEntity<ErrorResponse> handleMedicationException(MedicationException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), "System Rule Violation", ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(ex.getErrorCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> String.format("%s: %s",  err.getField() , err.getDefaultMessage() ))
                .toList();
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                "Invalid input data",
                request.getDescription(false)
        );
        error.setValidationErrors(errors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> String.format("%s: %s", violation.getPropertyPath(), violation.getMessage() ) )
                .toList();
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                "Invalid input data",
                request.getDescription(false)
        );
        error.setValidationErrors(errors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxUploadSizeException(MaxUploadSizeExceededException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "File Size Error",
                "Uploaded file size exceeds the maximum allowed limit",
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllUncaughtExceptions(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Server Error",
                "An unexpected error occurred",
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
