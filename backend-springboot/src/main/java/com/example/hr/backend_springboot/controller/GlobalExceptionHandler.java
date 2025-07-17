package com.example.hr.backend_springboot.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/general";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationException(MethodArgumentNotValidException ex, Model model) {
        model.addAttribute("errorMessage", "Validation failed: " + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return "error/general";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolation(DataIntegrityViolationException ex, Model model) {
        model.addAttribute("errorMessage", "Database error: " + ex.getRootCause().getMessage());
        return "error/general";
    }
} 