package com.epam.atithi.exception;

public class QRCodeGenerationException extends RuntimeException {
    public QRCodeGenerationException(String message) {
        super(message);
    }

    public QRCodeGenerationException() {
        super();
    }
}
