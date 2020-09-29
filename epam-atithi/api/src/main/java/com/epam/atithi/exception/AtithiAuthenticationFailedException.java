package com.epam.atithi.exception;

public class AtithiAuthenticationFailedException extends RuntimeException {

    public AtithiAuthenticationFailedException(String message) {
        super(message);
    }

    public AtithiAuthenticationFailedException() {
        super();
    }
}
