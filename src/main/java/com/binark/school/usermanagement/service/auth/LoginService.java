package com.binark.school.usermanagement.service.auth;

import com.binark.school.usermanagement.controller.response.LoginResponse;
import com.binark.school.usermanagement.exception.AuthenticationException;
import com.binark.school.usermanagement.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    LoginResponse processLogin(String username, String password) throws UserNotFoundException, AuthenticationException;
    LoginResponse processLogin(String username, String password, boolean rememberMe) throws UserNotFoundException, AuthenticationException;
}
