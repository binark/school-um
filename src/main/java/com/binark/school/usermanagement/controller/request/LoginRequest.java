package com.binark.school.usermanagement.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private boolean rememberMe;
}
