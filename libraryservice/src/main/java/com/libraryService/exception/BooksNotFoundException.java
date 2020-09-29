package com.libraryService.exception;

public class BooksNotFoundException extends RuntimeException {

    public BooksNotFoundException() {
        super("Book Not Found....");
    }

    public BooksNotFoundException(String message) {
        super( message );
    }
}
