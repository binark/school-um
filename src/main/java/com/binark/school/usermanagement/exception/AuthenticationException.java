package com.binark.school.usermanagement.exception;

import javax.ws.rs.BadRequestException;

public class AuthenticationException extends Exception {

    public AuthenticationException(String message) {
        super(message);
    }
}
