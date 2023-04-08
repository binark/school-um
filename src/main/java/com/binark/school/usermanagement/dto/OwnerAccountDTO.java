package com.binark.school.usermanagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OwnerAccountDTO {

    private Long id;

    private String slug;

    private String password;

    private String passwordConfirm;

    private String firstname;

    @NotBlank(message = "The lastname counld not be empty or null")
    @Size(min = 2, message = "The lastname should have at least two characters")
    private String lastname;

    @PositiveOrZero(message = "School number can not be a negative number")
    private Integer authorizedSchool;

    @Email(message = "You should provide a valid email address")
    private String email;

    @NotBlank(message = "You should provide a phone number")
    private String phoneNumber;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean enabled;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean deleted;
}
