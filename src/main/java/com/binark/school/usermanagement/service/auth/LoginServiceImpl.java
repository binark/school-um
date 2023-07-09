package com.binark.school.usermanagement.service.auth;

import com.binark.school.usermanagement.controller.response.LoginResponse;
import com.binark.school.usermanagement.controller.response.UserResponse;
import com.binark.school.usermanagement.entity.Account;
import com.binark.school.usermanagement.exception.AuthenticationException;
import com.binark.school.usermanagement.exception.UserNotFoundException;
import com.binark.school.usermanagement.mapper.AccountMapper;
import com.binark.school.usermanagement.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final OAuth2Manager oAuth2Manager;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final AccountMapper mapper;

    @Override
    public LoginResponse processLogin(String username, String password, boolean rememberMe) throws AuthenticationException {

        Account userAccount = this.getUserAccount(username);

        this.checkPasswordMatching(userAccount, password);

        // TODO setup remember me action

        // Get access token
        TokenResponse response = this.oAuth2Manager.getAccessToken(username, password);

        UserResponse user = mapper.mapAccountToUserResponse(userAccount);

        return LoginResponse.builder()
                .accessToken(response.getAccessToken())
                .refreshToken(response.getRefreshToken())
                .user(user)
                .build();


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

}
