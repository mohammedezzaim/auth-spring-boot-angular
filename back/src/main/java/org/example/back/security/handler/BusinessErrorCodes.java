package org.example.back.security.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Ezzaim Mohammed
 **/
public enum BusinessErrorCodes {

    NO_CODE("NO_CODE", NOT_IMPLEMENTED, "No type"),
    INCORRECT_CURRENT_PASSWORD("INCORRECT_CURRENT_PASSWORD", BAD_REQUEST, "Current password is incorrect"),
    NEW_PASSWORD_DOES_NOT_MATCH("NEW_PASSWORD_DOES_NOT_MATCH", BAD_REQUEST, "The new password does not match"),
    ACCOUNT_LOCKED("ACCOUNT_LOCKED", FORBIDDEN, "User account is locked"),
    ACCOUNT_DISABLED("ACCOUNT_DISABLED", FORBIDDEN, "User account is disabled"),
    BAD_CREDENTIALS("BAD_CREDENTIALS", FORBIDDEN, "Login and / or Password is incorrect"),
    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS", CONFLICT, "Email already exists"),
    VALIDATION_ERRORS("VALIDATION_ERRORS", BAD_REQUEST, "Input is not valid");
    ;

    @Getter
    private final String type;
    @Getter
    private final String description;
    @Getter
    private final HttpStatus httpStatus;

    BusinessErrorCodes(String type, HttpStatus status, String description) {
        this.type = type;
        this.description = description;
        this.httpStatus = status;
    }
}
