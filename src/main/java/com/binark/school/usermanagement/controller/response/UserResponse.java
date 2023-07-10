package com.binark.school.usermanagement.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse{

    private String email;

    private String firstname;

    private String lastname;

    private String slug;

    private String username;

    private Set<String> roles = new HashSet<>();
    private Set<String> schools;
}
