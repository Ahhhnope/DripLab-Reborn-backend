package com.example.cafe.Exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomResponseEntityExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorDetails> handleResourceNotFound(WebRequest request, Exception ex) {
        HttpServletRequest r = ((ServletWebRequest)request).getRequest();
        CustomErrorDetails ced = new CustomErrorDetails(LocalDateTime.now(), ex.getMessage(), r.getRequestURL().toString());
        return new ResponseEntity<>(ced, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        HttpServletRequest r = ((ServletWebRequest)request).getRequest();

        String messages = ex.getFieldErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining(", "));
        CustomErrorDetails errorDetails = new CustomErrorDetails(LocalDateTime.now(), messages, r.getRequestURL().toString());

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
