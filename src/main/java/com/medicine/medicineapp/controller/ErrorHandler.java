package com.medicine.medicineapp.controller;

import java.sql.SQLException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.medicine.medicineapp.exception.DataInsertionException;
import com.medicine.medicineapp.exception.DataNotFoundException;
import com.medicine.medicineapp.exception.NotAuthorizedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@CrossOrigin
public class ErrorHandler {

    private HttpServletResponse buildResponseFromCookie(HttpServletRequest request, HttpServletResponse response)
    {
        if(request.getCookies() == null)
            return response;
        String id = request.getCookies()[0].getValue();
        Cookie cookie = new Cookie(request.getCookies()[0].getName(),id);
        response.addCookie(cookie);
        return response;
    }
    
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<String> handleDataNotFoundException(DataNotFoundException e, HttpServletRequest request, HttpServletResponse response)
    {
        response = buildResponseFromCookie(request, response);
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataInsertionException.class)
    public ResponseEntity<String> handleDataInsertionException(DataInsertionException e, HttpServletRequest request, HttpServletResponse response)
    {
        response = buildResponseFromCookie(request, response);
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<String> handleNotAuthorizedexception(NotAuthorizedException e, HttpServletRequest request, HttpServletResponse response)
    {
        response = buildResponseFromCookie(request, response);
        return new ResponseEntity<>(e.getMessage(),HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSQLException(SQLException e, HttpServletRequest request, HttpServletResponse response)
    {
        response = buildResponseFromCookie(request, response);
        return new ResponseEntity<>(e.getMessage(),HttpStatus.SERVICE_UNAVAILABLE);
    }

}