package com.binark.school.usermanagement.service.auth;

import com.binark.school.usermanagement.controller.response.BaseResponse;
import com.binark.school.usermanagement.controller.response.LoginResponse;
import com.binark.school.usermanagement.controller.response.UserResponse;
import com.binark.school.usermanagement.exception.AuthenticationException;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    ResponseEntity<BaseResponse> processLogin(String username, String password, boolean rememberMe)throws AuthenticationException;
}
