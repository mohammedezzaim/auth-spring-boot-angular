package org.example.back.security.handler;

/**
 * @author Ezzaim Mohammed
 **/

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}