package com.epam.atithi.exception;

public class InvitationNotFoundException extends RuntimeException {

    public InvitationNotFoundException() {
        super("There are no invitation present in database...");
    }

    public InvitationNotFoundException(String message) {
        super(message);
    }

    public InvitationNotFoundException(Long invitationId) {
        super("There are no invitation present in database for the invitation id : "+invitationId);
    }
}
