package com.binark.school.usermanagement.service.auth;

import com.binark.school.usermanagement.controller.response.LoginResponse;
import com.binark.school.usermanagement.controller.response.UserResponse;
import com.binark.school.usermanagement.exception.AuthenticationException;
import com.binark.school.usermanagement.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private final OAuth2Manager oAuth2Manager;

    public LoginServiceImpl(OAuth2Manager oAuth2Manager) {
        this.oAuth2Manager = oAuth2Manager;
    }

    @Override
    public LoginResponse processLogin(String username, String password) throws UserNotFoundException, AuthenticationException {
        return processLogin(username, password, false);
    }

    @Override
    public LoginResponse processLogin(String username, String password, boolean rememberMe) throws UserNotFoundException, AuthenticationException {

        System.out.println("************************** login service *******************************");
        TokenResponse response = this.oAuth2Manager.getAccessToken(username, password);

        System.out.println("**************************** token returned *********************");

        UserResponse user = this.oAuth2Manager.getUserInfo(response.getAccessToken(), username);

        return LoginResponse.builder()
                .accessToken(response.getAccessToken())
                .user(user)
                .build();


    }

}
