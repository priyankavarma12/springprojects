package com.libraryService.exception;

public class NoBooksFoundException extends RuntimeException {

    public NoBooksFoundException() {
        super("There are no books present in database...");
    }

    public NoBooksFoundException(String message) {
        super(message);
    }
}
