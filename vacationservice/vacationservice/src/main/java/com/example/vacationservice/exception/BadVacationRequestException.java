package com.example.vacationservice.exception;

public class BadVacationRequestException extends RuntimeException {

    String message;
    String error;

    public BadVacationRequestException(String message, String error) {
        this.message = message;
        this.error = error;
    }
    public BadVacationRequestException(String message) {
        super(message);
    }
}
