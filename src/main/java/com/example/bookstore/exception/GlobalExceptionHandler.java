package com.example.bookstore.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public String handleBusinessLogicException(CustomException exception, WebRequest request, Model model) {
        model.addAttribute("message", exception.getMessage());
        model.addAttribute("status", exception.getStatus());
        log.error("Error when request: {}", request.getDescription(false).replace("uri=", ""));
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception, WebRequest request, Model model) {
        model.addAttribute("message", exception.getMessage());
        model.addAttribute("status", 500);
        log.error("Error when request: {}", request.getDescription(false).replace("uri=", ""));
        return "error";
    }

}
