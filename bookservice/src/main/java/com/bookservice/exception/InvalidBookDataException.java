package com.bookservice.exception;

/**
 * Exception thrown when there are required fields missing in Book Model
 *
 * @author Priyanka Varma
 */
public class InvalidBookDataException extends RuntimeException {
    public InvalidBookDataException(String message) {
        super(message);
    }
}
