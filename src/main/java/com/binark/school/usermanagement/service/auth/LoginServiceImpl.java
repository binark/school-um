package com.binark.school.usermanagement.service.auth;

import com.binark.school.usermanagement.controller.response.BaseResponse;
import com.binark.school.usermanagement.controller.response.LoginResponse;
import com.binark.school.usermanagement.controller.response.UserResponse;
import com.binark.school.usermanagement.entity.Account;
import com.binark.school.usermanagement.exception.AuthenticationException;
import com.binark.school.usermanagement.exception.UserNotFoundException;
import com.binark.school.usermanagement.mapper.AccountMapper;
import com.binark.school.usermanagement.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final OAuth2Manager oAuth2Manager;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final AccountMapper mapper;
    private final AccountRoleResolver accountRoleResolver;

    @Override
    public ResponseEntity<BaseResponse> processLogin(String username, String password, boolean rememberMe) throws AuthenticationException {

        Account userAccount = this.getUserAccount(username);

        this.checkPasswordMatching(userAccount, password);

        // TODO setup remember me action

        // Get access token
        TokenResponse response = this.oAuth2Manager.getAccessToken(username, password);

        Set<String> accountRoles = accountRoleResolver.getAccountRoles(response.getAccessToken());
        Set<String> schools = accountRoleResolver.getAccountSchools(response.getAccessToken());

        UserResponse user = mapper.mapAccountToUserResponse(userAccount);
        user.setRoles(accountRoles);
        user.setSchools(schools);
        HttpHeaders headers = new HttpHeaders();

        // Add access token
        headers.add(HttpHeaders.SET_COOKIE,
                createHttpOnlyCookie(response.getAccessToken(), "TOKEN", response.getExpiresIn())
                        .toString());

        // Add refresh token
        headers.add(HttpHeaders.SET_COOKIE,
                createHttpOnlyCookie(response.getRefreshToken(), "RF-TOKEN", response.getRefreshExpriesIn())
                        .toString());

        BaseResponse baseResponse = BaseResponse.builder()
                                                            .message("login successful")
                                                            .data(user)
                                                            .build();

        return ResponseEntity.ok().headers(headers).body(baseResponse);


    }

    private Account getUserAccount(String identifier) throws AuthenticationException {
        try {
            return this.accountService.findByIdentifier(identifier);
        }catch (UserNotFoundException unf) {
            throw new AuthenticationException();
        }
    }

    private void checkPasswordMatching(Account account, String password) throws AuthenticationException {
        if (!this.passwordEncoder.matches(password, account.getPassword())) {
            log.debug("Login process, password doesn't match");
            throw new AuthenticationException();
        }
    }

    private HttpCookie createHttpOnlyCookie(String token, String name, long duration) {

        return ResponseCookie.from(name, token)
                .httpOnly(true)
                .maxAge(duration)
                .secure(true)
                .path("/")
                .build();
    }

}
