package com.libraryService.exception;

public class NoUsersFoundException extends RuntimeException {

    public NoUsersFoundException() {
        super("There are no users present in database...");
    }

    public NoUsersFoundException(String message) {
        super( message );
    }
}
