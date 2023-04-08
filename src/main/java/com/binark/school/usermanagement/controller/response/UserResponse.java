package com.binark.school.usermanagement.controller.response;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class UserResponse{

    private String email;

    private String firstname;

    private String lastname;

    private String slug;

    private String username;

    private List<String> roles = new ArrayList<>();

    private List<String> requiredActions = new ArrayList<>();
}
