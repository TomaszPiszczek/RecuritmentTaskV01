package com.example.recruitmenttaskv01.exception.globalHandler;

import com.example.recruitmenttaskv01.exception.ExceptionResponse;
import com.example.recruitmenttaskv01.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RepositoryControllerExceptionHandler {

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                notFound.toString(),
                e.getMessage()
        );
        return new ResponseEntity<>(exceptionResponse, notFound);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> handleUserNullException(NullPointerException e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                notFound.toString(),
                e.getMessage()
        );
        return new ResponseEntity<>(exceptionResponse, notFound);
    }


}
