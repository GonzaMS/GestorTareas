package com.proyecto.gestor.controllers;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleEntityNotFoundException(EntityNotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(AuthenticationException.class)
    public ModelAndView handleAuthenticationError(Exception ex){
        ModelAndView modelAndView = new ModelAndView("error"); // Página de error 500 personalizada
        modelAndView.addObject("errorMessage", "Error de autenticacion");
        return modelAndView;
    }

}
