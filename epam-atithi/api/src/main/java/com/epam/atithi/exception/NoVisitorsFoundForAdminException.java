package com.epam.atithi.exception;

/**
 * Exception thrown when there are no users found in the system
 *
 * @author Spallya Omar
 */
public class NoVisitorsFoundForAdminException extends RuntimeException {

    public NoVisitorsFoundForAdminException() {
        super("There are no visitors present in database...");
    }

    public NoVisitorsFoundForAdminException(String message) {
        super(message);
    }

    public NoVisitorsFoundForAdminException(Long adminId) {
        super("There are no visitors present in database for the admin id : "+adminId);
    }
}
