package com.epam.atithi.exception;

public class UnableToUpdateDataException extends RuntimeException {
    public UnableToUpdateDataException(String message) {
        super(message);
    }

    public UnableToUpdateDataException() {
        super();
    }
}
