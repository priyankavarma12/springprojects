package com.epam.atithi.exception;

/**
 * Exception thrown when the user of a particular id is not found in the system
 * 
 * @author Spallya Omar
 */
public class VisitorNotFoundException extends RuntimeException {

    public VisitorNotFoundException() {
        super("Visitor Not Found...");
    }

    public VisitorNotFoundException(String message) {
        super(message);
    }

    public VisitorNotFoundException(Long visitorId) {
        super("Visitor Not Found with id : "+visitorId );
    }

}
