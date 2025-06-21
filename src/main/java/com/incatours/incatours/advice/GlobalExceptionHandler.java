package com.incatours.incatours.advice;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle404() {
        return "error/404";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handle403() {
        return "error/403";
    }

    // Manejo genérico para cualquier excepción
    @ExceptionHandler(Exception.class)
    public String manejarExcepciones(Exception ex, Model model) {
        model.addAttribute("mensajeError", "Ocurrió un error inesperado: " + ex.getMessage());
        return "error/generico";
    }
}
