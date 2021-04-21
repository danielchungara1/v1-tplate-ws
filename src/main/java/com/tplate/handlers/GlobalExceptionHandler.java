package com.tplate.handlers;

import com.tplate.layers.a.rest.dtos.SimpleResponseDto;
import com.tplate.layers.b.business.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Controller
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    // Business Exceptions
    @ExceptionHandler({
            BusinessException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SimpleResponseDto badRequestExceptionHandler(BusinessException e) {
        log.error(e.getMessage());
        return SimpleResponseDto.builder()
                .message(e.getMessage())
                .details(e.getDetails())
                .build();
    }

    // Invalid Path Variable URL
    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SimpleResponseDto MethodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e) {
        log.error(e.getMessage());
        return SimpleResponseDto.builder()
                .message("Invalid URL.")
                .details("Check parameters sent in the URL." + e.getMessage())
                .build();
    }

    // Invalid Json Body
    @ExceptionHandler({
            HttpMessageNotReadableException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SimpleResponseDto HttpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        log.error(e.getMessage());
        return SimpleResponseDto.builder()
                .message("Invalid JSON body.")
                .details( e.getMessage())
                .build();
    }

    // Invalid Dto
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SimpleResponseDto methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        return SimpleResponseDto.builder()
                .message(e.hasErrors() ? e.getAllErrors().get(0).getDefaultMessage() :"Method Argument not valid.")
                .details(e.getMessage())
                .build();
    }

    // URL Not Found
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public SimpleResponseDto notFoundExceptionHandler(NoHandlerFoundException e, WebRequest request) {
        log.error(e.getMessage());
        return SimpleResponseDto.builder()
                .message("Resource not found.")
                .details(e.getRequestURL())
                .build();
    }

    // Access Denied
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public SimpleResponseDto forbiddenExceptionHandler(AccessDeniedException e) {
        log.error(e.getMessage());
        return SimpleResponseDto.builder()
                .message("Access denied.")
                .details(e.getMessage())
                .build();
    }

    // Internal Server Error
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SimpleResponseDto allExceptionHandler(Exception e) {
        e.printStackTrace();
        return SimpleResponseDto.builder()
                .message("Internal server error.")
                .details(e.getMessage())
                .build();
    }

}
