package com.binark.school.usermanagement.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.vault.authentication.UsernamePasswordAuthentication;

import java.io.IOException;
import java.util.stream.Collectors;

public class AdminAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String ADMIN_LOGIN_URL = "/login/**";

    protected AdminAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(ADMIN_LOGIN_URL, "POST"), authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String bodyString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        System.out.println("************************************** admin filter");
        System.out.println("************************************** data:  " + bodyString);

        Authentication authentication = this.getAuthenticationManager().authenticate(new AdminAuthentication("test@test.com", "password"));

        return authentication;
    }
}
