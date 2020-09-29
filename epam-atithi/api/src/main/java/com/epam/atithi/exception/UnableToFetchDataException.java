package com.epam.atithi.exception;

public class UnableToFetchDataException extends RuntimeException {
    public UnableToFetchDataException(String message) {
        super(message);
    }

    public UnableToFetchDataException() {
        super();
    }
}
