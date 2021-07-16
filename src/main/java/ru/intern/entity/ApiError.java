package ru.intern.entity;

import org.springframework.http.HttpStatus;

/**
 * @author Kir
 * Created on 16.07.2021
 */

public class ApiError {
    private HttpStatus status;
    private String message;


    public static final class ApiErrorBuilder {
        private HttpStatus status;
        private String message;

        private ApiErrorBuilder() {
        }

        public static ApiErrorBuilder anApiError() {
            return new ApiErrorBuilder();
        }

        public ApiErrorBuilder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public ApiErrorBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ApiError build() {
            ApiError apiError = new ApiError();
            apiError.status = this.status;
            apiError.message = this.message;
            return apiError;
        }
    }
}
