package com.binark.school.usermanagement.config;

import com.binark.school.usermanagement.service.auth.OAuth2Manager;
import com.binark.school.usermanagement.service.auth.TokenResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class OAuth2AuthenticationFilter extends GenericFilterBean {

    private final OAuth2Manager tokenManager;

    public OAuth2AuthenticationFilter(OAuth2Manager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String username = (String) servletRequest.getAttribute("username");
        String password = (String) servletRequest.getAttribute("password");

//        ResponseEntity<TokenResponse> response = this.tokenManager.getAccessToken(username, password);
//
//        if (!response.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
//            throw new ServletException("Impossible authentication");
//        }
//
//        TokenResponse tokenResponse = response.getBody();

    }
}
