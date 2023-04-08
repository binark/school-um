package com.binark.school.usermanagement.controller.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;

    private String password;

    private boolean rememberMe;
}
