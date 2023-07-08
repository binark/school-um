package com.binark.school.usermanagement.exception;

public class SchoolBadRequestException extends Exception{

    public SchoolBadRequestException() {
        super("bad request parameters");
    }

    public SchoolBadRequestException(String message) {
        super(message);
    }
}
