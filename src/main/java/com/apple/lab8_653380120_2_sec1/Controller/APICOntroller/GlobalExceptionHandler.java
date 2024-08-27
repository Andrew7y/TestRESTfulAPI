package com.apple.lab8_653380120_2_sec1.Controller.APICOntroller;

import com.apple.lab8_653380120_2_sec1.Exception.AuthorException;
import com.apple.lab8_653380120_2_sec1.Exception.BookException;
import com.apple.lab8_653380120_2_sec1.Exception.BookNotFoundException;
import com.apple.lab8_653380120_2_sec1.Exception.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler(AuthorException.class)
    public ResponseEntity<String> handleAuthorException(AuthorException ex) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookException.class)
    public ResponseEntity<String> handleAuthorException(BookException ex) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        String errorMessage = "Method " + ex.getMethod() + " is not supported for this endpoint.";
        return new ResponseEntity<>(new CustomErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(),
                errorMessage),
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNotFound(NoHandlerFoundException ex) {
        String errorMessage = "Resource not found: " + ex.getRequestURL();
        return new ResponseEntity<>(new CustomErrorResponse(HttpStatus.NOT_FOUND.value(),
                errorMessage),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String errorMessage = "Invalid value for parameter '" + ex.getName() + "': "
                + ex.getValue() + ". Expected type: " + ex.getRequiredType().getName();
        return new ResponseEntity<>(new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(),
                errorMessage),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<String> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        return new ResponseEntity<>(
                "Unsupported Media Type: "
                + ex.getContentType(),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

}
