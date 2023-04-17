package com.binark.school.usermanagement.controller.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginRequest {

    private String username;

    private String password;

    private boolean rememberMe;
}
