package com.binark.school.usermanagement.service.auth;

import com.binark.school.usermanagement.controller.response.LoginResponse;
import com.binark.school.usermanagement.exception.AuthenticationException;

public interface LoginService {

    LoginResponse processLogin(String username, String password, boolean rememberMe)throws AuthenticationException;
}
