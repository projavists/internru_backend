package ru.intern.config;

import javax.xml.bind.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.intern.entity.ApiError;

/**
 * @author Kir
 * Created on 16.07.2021
 */

@ControllerAdvice
public class GlobalExceptionConfig extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionConfig.class);
    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handle(ValidationException ex) {
        return new ResponseEntity<>(getError(HttpStatus.BAD_REQUEST, ex), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handle(Exception ex, WebRequest request) {
        try {
            return super.handleException(ex, request);
        } catch (Exception e) {
            return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
        }
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.Series.SERVER_ERROR == status.series()) {
            log.error("Unexpected error.", ex);
        }
        return new ResponseEntity<>(getError(status, ex), headers, status);
    }

    private ApiError getError(HttpStatus status, Exception ex) {
        return ApiError.ApiErrorBuilder.anApiError().status(status).message(ex.getMessage()).build();
    }

}
