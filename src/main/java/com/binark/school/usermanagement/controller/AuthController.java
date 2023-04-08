package com.binark.school.usermanagement.controller;

import com.binark.school.usermanagement.controller.request.LoginRequest;
import com.binark.school.usermanagement.controller.response.BaseResponse;
import com.binark.school.usermanagement.controller.response.LoginResponse;
import com.binark.school.usermanagement.exception.AuthenticationException;
import com.binark.school.usermanagement.exception.UserNotFoundException;
import com.binark.school.usermanagement.service.auth.LoginService;
import com.binark.school.usermanagement.service.auth.OAuth2Manager;
import com.binark.school.usermanagement.service.auth.TokenResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OAuth2Manager tokenManager;

    private final LoginService loginService;

    public AuthController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> authenticate(@RequestBody LoginRequest loginRequest) throws UserNotFoundException, AuthenticationException {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        boolean rememberMe = loginRequest.isRememberMe();

        LoginResponse response = this.loginService.processLogin(username, password, rememberMe);
        BaseResponse<LoginResponse> baseResponse = new BaseResponse();
        baseResponse.setData(response);
        baseResponse.setMessage("Connexion réussie!");

        return ResponseEntity.ok(baseResponse);

    }

    @GetMapping("/token")
    public ResponseEntity<TokenResponse> getAccessToken(@RequestParam("username") String username, @RequestParam("password") String password) throws AuthenticationException {

//        String token = tokenManager.getAccessToken();

        String credentials = "school-user-management:vbRdRK9SaOazFWAojBrgCaMolgSGosBq";
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes(), true));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
       // headers.setAccept(MediaType.APPLICATION_JSON);
        //headers.add("Authorization", "Basic " + encodedCredentials);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("username", "test@test.com");
        map.add("password", "test");
        map.add("client_id", "school-user-management");
        map.add("client_secret", "vbRdRK9SaOazFWAojBrgCaMolgSGosBq");
        HttpEntity<MultiValueMap> request = new HttpEntity<>(map, headers);
        String accessTokenUrl = "http://localhost:8080/realms/school-dev/protocol/openid-connect/token";

        TokenResponse accessToken = tokenManager.getAccessToken(username, password);

        return ResponseEntity.ok(accessToken);

        // return restTemplate.exchange(accessTokenUrl, HttpMethod.POST, request, TokenResponse.class);

//        return webClient
//                .get()
//                .uri("http://localhost:8080?username=test@test.com")
//                .attributes(
//                        ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId("keycloak")
//                )
//                .retrieve()
//                .bodyToMono(String.class);

       // return "token";
    }
}
