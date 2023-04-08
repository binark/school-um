package com.binark.school.usermanagement.exception;

import javax.ws.rs.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException() {
        super("L'utilisateur n'existe pas dans le système");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
