package com.binark.school.usermanagement.controller;

import com.binark.school.usermanagement.controller.request.CreatePasswordRequest;
import com.binark.school.usermanagement.controller.response.BaseResponse;
import com.binark.school.usermanagement.exception.ResetPasswordExpirationException;
import com.binark.school.usermanagement.exception.UserNotFoundException;
import com.binark.school.usermanagement.service.auth.PasswordManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordManagementService passwordManagementService;

    @PutMapping("/set-password/{slug}")
    public void createUserPassword(@PathVariable String slug, @Valid @RequestBody CreatePasswordRequest createPasswordRequest) throws UserNotFoundException {
        passwordManagementService.setUserPassword(slug, createPasswordRequest.getPassword());
    }

    /**
     *
     * @param passwordKey
     * @return the account slug
     * @throws UserNotFoundException
     * @throws ResetPasswordExpirationException
     */
    @GetMapping(value = "/request-reset-password/{passwordKey}")
    public BaseResponse requestResetPassword(@PathVariable String passwordKey) throws UserNotFoundException, ResetPasswordExpirationException {
        String slug = passwordManagementService.checkResetPasswordPossibility(passwordKey);
        return BaseResponse.builder().data(slug).build();
    }
}
