package ru.intern.entity;

import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * @author Kir
 * Created on 16.07.2021
 */

public class ResponseError {
    private HttpStatus status;
    private String message;
    private String stackTrace;
    private List<Exception> errors;


    public static final class ResponseErrorBuilder {
        private HttpStatus status;
        private String message;
        private String stackTrace;
        private List<Exception> errors;

        private ResponseErrorBuilder() {
        }

        public static ResponseErrorBuilder ResponseError() {
            return new ResponseErrorBuilder();
        }

        public ResponseErrorBuilder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public ResponseErrorBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ResponseErrorBuilder stackTrace(String stackTrace) {
            this.stackTrace = stackTrace;
            return this;
        }

        public ResponseErrorBuilder errors(List<Exception> errors) {
            this.errors = errors;
            return this;
        }

        public ResponseError build() {
            ResponseError responseError = new ResponseError();
            responseError.setStackTrace(stackTrace);
            responseError.status = this.status;
            responseError.message = this.message;
            responseError.errors = this.errors;
            return responseError;
        }
    }


    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public List<Exception> getErrors() {
        return errors;
    }

    public void setErrors(List<Exception> errors) {
        this.errors = errors;
    }
}
