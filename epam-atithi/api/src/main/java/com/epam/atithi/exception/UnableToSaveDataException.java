package com.epam.atithi.exception;

public class UnableToSaveDataException extends RuntimeException {

    public UnableToSaveDataException(String message) {
        super(message);
    }

    public UnableToSaveDataException() {
        super();
    }
}
