package com.binark.school.usermanagement.controller;

import com.binark.school.usermanagement.controller.request.LoginRequest;
import com.binark.school.usermanagement.controller.response.BaseResponse;
import com.binark.school.usermanagement.controller.response.LoginResponse;
import com.binark.school.usermanagement.exception.AuthenticationException;
import com.binark.school.usermanagement.exception.UserNotFoundException;
import com.binark.school.usermanagement.service.auth.AdminLoginService;
import com.binark.school.usermanagement.service.auth.LoginService;
import com.binark.school.usermanagement.service.auth.OAuth2Manager;
import com.binark.school.usermanagement.service.auth.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OAuth2Manager tokenManager;

    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    private SecurityContextRepository strategy =  new HttpSessionSecurityContextRepository();

    private final LoginService loginService;

    private final AdminLoginService adminLoginService;


   // public AuthController(LoginService loginService) {
//        this.loginService = loginService;
//    }

    @GetMapping("/admin/login")
    public String adminLogin(Model model) {
        model.addAttribute("login", new LoginRequest());
        return "auth/login";
    }

    @PostMapping("/admin/login/start")
    public String adminLoginStart(@ModelAttribute("login") LoginRequest login,
                                  Model model, HttpServletRequest request,
                                  HttpServletResponse response) throws AuthenticationException {
        String password = this.adminLoginService.startLoginProcess(login.getUsername());
        System.out.println("password = " + password);
        //  this.adminLoginService.processLogin(login.getUsername(), login.getPassword(), request, response);
        model.addAttribute("login", login);
        return "/auth/login-password";
    }

    @PostMapping("/admin/login/end")
    public String adminLogin(@ModelAttribute("login") LoginRequest login, Model model, HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, UserNotFoundException {

          this.adminLoginService.processLogin(login.getUsername(), login.getPassword(), request, response);
      //  model.addAttribute("login", login);
        return "redirect:/admin/owner";
    }

    @PostMapping("/login")
    @ResponseBody
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
    @ResponseBody
    public ResponseEntity<TokenResponse> getAccessToken(@RequestParam("username") String username, @RequestParam("password") String password) throws AuthenticationException {
        System.out.println("username = " + username);
        System.out.println("password = " + password);
//        String token = tokenManager.getAccessToken();

        String credentials = "school-user-management:vbRdRK9SaOazFWAojBrgCaMolgSGosBq";
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes(), true));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // headers.setAccept(MediaType.APPLICATION_JSON);
        //headers.add("Authorization", "Basic " + encodedCredentials);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("username", "sukuluadmin");
        map.add("password", "admin");
        map.add("client_id", "sukulu-um");
        //  map.add("client_secret", "vbRdRK9SaOazFWAojBrgCaMolgSGosBq");
        HttpEntity<MultiValueMap> request = new HttpEntity<>(map, headers);
        String accessTokenUrl = "http://localhost:8080/realms/sukulu/protocol/openid-connect/token";

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