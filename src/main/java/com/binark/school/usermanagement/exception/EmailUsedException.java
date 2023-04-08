package com.binark.school.usermanagement.exception;

public class EmailUsedException extends Exception{

    public EmailUsedException(String email) {
        super("The email address " + email + " is already used");
    }
}
