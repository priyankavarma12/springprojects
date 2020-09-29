package com.epam.atithi.exception;

/**
 * Exception thrown when there are required fields missing in User Model
 *
 * @author Spallya Omar
 */
public class AdminNotFoundException extends RuntimeException {

    public AdminNotFoundException() {
        super("There are no admin present in database...");
    }

    public AdminNotFoundException(String message) {
        super(message);
    }

    public AdminNotFoundException(Long adminId) {
        super("There are no admin present in database for the admin id : "+adminId);
    }
}
