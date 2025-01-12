package dev.javarush.backend_projects.personal_blog.infrastructure.web;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({
            DataIntegrityViolationException.class,
            ObjectOptimisticLockingFailureException.class
    })
    public ModelAndView handleConflict(HttpServletRequest request, Exception e) {
        ModelAndView modelAndView = new ModelAndView("error/409");
        modelAndView.addObject("url", request.getRequestURL());
        return modelAndView;
    }
}
