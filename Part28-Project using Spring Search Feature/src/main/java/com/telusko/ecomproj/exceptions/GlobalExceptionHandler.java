package com.telusko.ecomproj.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

// this intercepts exceptions thrown by the controllers
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EmployeeNotFoundException.class) // When there's an EmployeeNotFoundException thrown, this method will handle it
  public ResponseEntity<ErrorObject> handleEmployeeNotFoundException(EmployeeNotFoundException ex, WebRequest request) {
    ErrorObject error = new ErrorObject();
    error.setStatusCode(HttpStatus.NOT_FOUND.value());
    error.setMessage(ex.getMessage() + " understood????!!!!!!!!");
    error.setTimestamp(new java.util.Date());
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

}