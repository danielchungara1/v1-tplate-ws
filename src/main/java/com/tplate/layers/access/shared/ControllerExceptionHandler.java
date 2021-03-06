package com.tplate.layers.access.shared;

import com.tplate.layers.access.dtos.ResponseSimpleDto;
import com.tplate.layers.business.exceptions.BusinessException;
import com.tplate.layers.business.exceptions.BusinessRuntimeException;
import com.tplate.shared.UtilString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
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
public class ControllerExceptionHandler {

    // Business Checked Exceptions
    @ExceptionHandler({
            BusinessException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseSimpleDto badRequestExceptionHandler(BusinessException e) {
        log.error(e.getMessage());
        return ResponseSimpleDto.builder()
                .message(e.getMessage())
                .details(e.getDetails())
                .build();
    }

    // Business Runtime Exceptions
    @ExceptionHandler({
            BusinessRuntimeException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseSimpleDto badRequestExceptionHandler(BusinessRuntimeException e) {
        log.error(e.getMessage());
        return ResponseSimpleDto.builder()
                .message(e.getMessage())
                .details(e.getDetails())
                .build();
    }

    // Invalid Path Variable URL
    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseSimpleDto methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e) {
        log.error(e.getMessage());
        return ResponseSimpleDto.builder()
                .message("Invalid URL.")
                .details("Check parameters sent in the URL." + e.getMessage())
                .build();
    }

    // Invalid Path Variable URL
    @ExceptionHandler({
            BadCredentialsException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseSimpleDto badCredentialsExceptionExceptionHandler(BadCredentialsException e) {
        log.error(e.getMessage());
        return ResponseSimpleDto.builder()
                .message("Invalid credentials.")
                .details("Check credentials sent. " + e.getMessage())
                .build();
    }

    // Invalid Json Body
    @ExceptionHandler({
            HttpMessageNotReadableException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseSimpleDto httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        log.error(e.getMessage());
        final String message = "Invalid JSON body.";
        return ResponseSimpleDto.builder()
                .message(message)
                .details( UtilString.truncateBySubstringOrElseReturnDefaultString(e.getMessage(), "(class", message))
                .build();
    }

    // Invalid Dto
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseSimpleDto methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        return ResponseSimpleDto.builder()
                .message(e.hasErrors() ? e.getAllErrors().get(0).getDefaultMessage() :"Method Argument invalid.")
                .details(UtilString.truncateBySubstringOrElseReturnDefaultString(e.getMessage(), "in public", "Method Argument invalid."))
                .build();
    }

    // URL Not Found
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseSimpleDto notFoundExceptionHandler(NoHandlerFoundException e, WebRequest request) {
        log.error(e.getMessage());
        return ResponseSimpleDto.builder()
                .message("Resource not found.")
                .details(e.getRequestURL())
                .build();
    }

    // Access Denied
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseSimpleDto forbiddenExceptionHandler(AccessDeniedException e) {
        log.error(e.getMessage());
        return ResponseSimpleDto.builder()
                .message("Access denied.")
                .details(e.getMessage())
                .build();
    }

    // Internal Server Error
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseSimpleDto allExceptionHandler(Exception e) {
        e.printStackTrace();
        return ResponseSimpleDto.builder()
                .message("Unexpected Error.")
                .details(e.getMessage())
                .build();
    }

}
