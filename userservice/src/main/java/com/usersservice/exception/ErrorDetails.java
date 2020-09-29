package com.usersservice.exception;

public class ErrorDetails {

    private String message;
    private String errorCode;

    public ErrorDetails(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
