package com.binark.school.usermanagement.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse{

    private String accessToken;

    private UserResponse user;
}
