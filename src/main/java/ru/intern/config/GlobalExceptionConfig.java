package ru.intern.config;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.intern.entity.ResponseError;
import sun.plugin.dom.exception.InvalidStateException;

/**
 * @author Kir
 * Created on 16.07.2021
 */

@ControllerAdvice
public class GlobalExceptionConfig extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionConfig.class);

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handle(BadCredentialsException exception, HttpServletRequest webRequest) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.toString());

    }

    @ExceptionHandler(MethodNotAllowedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handle(MethodNotAllowedException exception, HttpServletRequest webRequest) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.toString());

    }

    @ExceptionHandler(InvalidStateException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handle(InvalidStateException exception, HttpServletRequest webRequest) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.toString());

    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleAllUncaughtException(
            Exception exception,
            WebRequest request) {
        log.error("Unknown error occurred", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.toString());
    }


}
