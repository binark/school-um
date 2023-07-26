package com.binark.school.usermanagement.controller;

import com.binark.school.usermanagement.controller.response.BaseResponse;
import com.binark.school.usermanagement.exception.UserNotFoundException;
import com.binark.school.usermanagement.service.account.AccountService;
import com.binark.school.usermanagement.service.account.impl.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountServiceImpl accountService;

    @PutMapping("/add-school/{userSlug}/{schoolSlug}")
    public ResponseEntity<BaseResponse> addSchool(@PathVariable String userSlug, @PathVariable String schoolSlug) throws UserNotFoundException {

        accountService.addSchoolToAccount(userSlug, schoolSlug);

        BaseResponse baseResponse = BaseResponse.builder()
                .message("school added successful")
                .build();

        return ResponseEntity.ok(baseResponse);
    }
}
