package ru.denfad.UrlShortener.controllers;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.denfad.UrlShortener.dto.ExceptionResponse;
import ru.denfad.UrlShortener.exception.UrlNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(new Date(), "SERVER ERROR", HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Object> methodValidException(MethodArgumentNotValidException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(new Date(), exception.getBody().getDetail(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }


    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<ExceptionResponse> notFoundException(UrlNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(new Date(), exception.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionResponse> validationException(ValidationException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(new Date(), exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST.value()));
    }

}
