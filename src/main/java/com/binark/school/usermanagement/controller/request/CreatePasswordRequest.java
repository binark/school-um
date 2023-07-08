package com.binark.school.usermanagement.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreatePasswordRequest {

    @NotBlank
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*#?&^_-]).{8,32}", message = "The password should contains a lowercase, a uppercase, a number a special character. Minimum 8 characters")
    private String password;
}
