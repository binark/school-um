package com.binark.school.usermanagement.exception;

public class AuthenticationException extends SchoolBadRequestException {

    public AuthenticationException() {
        super("Incorrect username or password");
    }

    public AuthenticationException(String message) {
        super(message);
    }
}
