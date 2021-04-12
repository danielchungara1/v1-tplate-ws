package com.tplate.layers.b.business.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tplate.layers.a.rest.dtos.MessageDto;
import com.tplate.layers.b.business.builders.ResponseBuilder;
import com.tplate.layers.b.business.exceptions.restexceptions.RestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
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
public class ExceptionHandlerGlobal {

    @ExceptionHandler({
            RestException.class, // Business Exceptions
            MethodArgumentTypeMismatchException.class, // Path Variable Data Types
            HttpMessageNotReadableException.class // Incompatible datatype between dto-controller and json-received
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageDto badRequestExceptionHandler(Exception e) {
        log.error(e.getMessage());
        return MessageDto.builder().message(e.getMessage()).build();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageDto notFoundExceptionHandler(NoHandlerFoundException e, WebRequest request) {
        log.error(e.getMessage());
        return MessageDto.builder().message("Resource not found . " + e.getRequestURL()).build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public MessageDto forbiddenExceptionHandler(AccessDeniedException e) {
        log.error(e.getMessage());
        return MessageDto.builder().message("Forbidden. Access is denied.").build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public MessageDto allExceptionHandler(Exception e) {
        e.printStackTrace();
        return MessageDto.builder().message("Internal server error. " + e.getMessage()).build();
    }

}
