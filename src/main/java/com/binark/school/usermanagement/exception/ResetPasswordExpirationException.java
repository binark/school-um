package com.binark.school.usermanagement.exception;

public class ResetPasswordExpirationException extends SchoolBadRequestException{

    public ResetPasswordExpirationException() {
        super("The password reset link has expired");
    }
}
