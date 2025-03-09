package dev.javarush.roadmapsh_projects.blogging_platform_api.configuration.web;

import dev.javarush.roadmapsh_projects.blogging_platform_api.exceptions.BadRequestException;
import dev.javarush.roadmapsh_projects.blogging_platform_api.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.Objects;

@RestControllerAdvice
public class GlobalControllerAdvice {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(GlobalControllerAdvice.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        var pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getFieldError().getDefaultMessage());
        pd.setProperty(
                "errors",
                exception.getFieldErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .filter(Objects::nonNull)
                        .toList()
        );
        return pd;
    }

    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail handleBadRequestException(BadRequestException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getLocalizedMessage());
    }
}
