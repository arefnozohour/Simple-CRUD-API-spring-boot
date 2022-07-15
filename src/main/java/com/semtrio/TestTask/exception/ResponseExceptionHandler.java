package com.semtrio.TestTask.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ResponseExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ExceptionResponseModel> handleError(ServiceException e) {

        return ResponseEntity.ok(new ExceptionResponseModel(e.getStatus(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseModel> handleValidError(MethodArgumentNotValidException e) {

        return ResponseEntity.ok(new ExceptionResponseModel(e.getBindingResult()));
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponseModel> handleValidError1(DataIntegrityViolationException e) {

        ConstraintViolationException exception = (ConstraintViolationException) e.getCause();
        return ResponseEntity.ok(new ExceptionResponseModel(ExceptionData.get(exception.getConstraintName())));
    }
}
