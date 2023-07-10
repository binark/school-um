package com.binark.school.usermanagement.controller;

import com.binark.school.usermanagement.controller.request.CreatePasswordRequest;
import com.binark.school.usermanagement.controller.response.BaseResponse;
import com.binark.school.usermanagement.exception.ResetPasswordExpirationException;
import com.binark.school.usermanagement.exception.UserNotFoundException;
import com.binark.school.usermanagement.service.auth.PasswordManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordManagementService passwordManagementService;

    /**
     * The purpose is to allow user to set his password for the first time
     * @param slug The user slug
     * @param createPasswordRequest {@link CreatePasswordRequest} containing the user password
     * @throws UserNotFoundException If user is not found by the given slug
     */
    @PutMapping("/set-password/{slug}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createUserPassword(@PathVariable String slug, @Valid @RequestBody CreatePasswordRequest createPasswordRequest) throws UserNotFoundException {
        passwordManagementService.setUserPassword(slug, createPasswordRequest.getPassword());
    }

    /**
     * Request reset password: pass the password token and get the user slug
     * if the token is correct and didn't expire
     * @param passwordKey The password token
     * @return the account slug
     * @throws UserNotFoundException If user is not found by the slug
     * @throws ResetPasswordExpirationException If token expired
     */
    @GetMapping(value = "/request-reset-password/{passwordKey}")
    public BaseResponse requestResetPassword(@PathVariable String passwordKey) throws UserNotFoundException, ResetPasswordExpirationException {
        String slug = passwordManagementService.checkResetPasswordPossibility(passwordKey);
        return BaseResponse.builder().data(slug).build();
    }
}
