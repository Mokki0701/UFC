package edu.kh.cooof.common.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NoResourceFoundException.class)
    public String notFound(Model model) {
        model.addAttribute("error", "Page Not Found");
        model.addAttribute("message", "The page you are looking for does not exist.");
        return "error/404";
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequest(Exception ex, Model model) {
        model.addAttribute("error", "Bad Request");
        model.addAttribute("message", ex.getMessage());
        return "error/400";
    }

    @ExceptionHandler(Exception.class)
    public String allExceptionHandler(Exception ex, Model model) {
        model.addAttribute("error", "Internal Server Error");
        model.addAttribute("message", ex.getMessage());
        return "error/500";
    }
}
