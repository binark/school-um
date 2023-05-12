package com.binark.school.usermanagement.service.auth;

import com.binark.school.usermanagement.config.AdminLoginProcess;
import com.binark.school.usermanagement.controller.response.LoginResponse;
import com.binark.school.usermanagement.exception.AuthenticationException;
import com.binark.school.usermanagement.exception.UserNotFoundException;
import com.binark.school.usermanagement.publisher.AdminLoginFirstStepPublisher;
import com.binark.school.usermanagement.publisher.AdminPublisher;
import com.binark.school.usermanagement.publisher.AdminWrongEmailPublisher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminLoginService{

    private final AuthenticationManager authenticationManager;

    private final AdminLoginFirstStepPublisher publisher;

    private final AdminWrongEmailPublisher wrongEmailPublisher;

    private final AdminLoginProcess adminLoginProcess;

    private SecurityContextRepository strategy =  new HttpSessionSecurityContextRepository();

    // Admin Email defined in vault
    @Value("${admin.key}")
    private String adminKey;

    public String startLoginProcess(String email) throws AuthenticationException {

        if (email == null || !email.equals(adminKey)) {
            log.warn("Someone tried to connect to admin account with wrong mail:  " + email);
            this.wrongEmailPublisher.publsh(adminKey, email);
            //throw new AuthenticationException(email);
            return "";
        }
        String password = UUID.randomUUID().toString();
        this.adminLoginProcess.setCredential(password.toUpperCase());
        publisher.publsh(email);

        return password;
    }

    public void processLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) throws UserNotFoundException, AuthenticationException {
        this.processLogin(username, password, false, request, response);
    }

    public void processLogin(String username, String password, boolean rememberMe, HttpServletRequest request, HttpServletResponse response) throws UserNotFoundException, AuthenticationException {

        log.debug("**************** Login as admin");

        if (this.adminLoginProcess.getCredential() == null) {
            throw new AuthenticationException("Authentification failed");
        }

        if (password == null || !password.equals(this.adminLoginProcess.getCredential())) {
            throw new AuthenticationException("Authentification failed");
        }

        SecurityContextHolderStrategy holderStrategy = SecurityContextHolder.getContextHolderStrategy();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken
                .unauthenticated(username, password);
        Authentication authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        holderStrategy.setContext(context);
        strategy.saveContext(context, request, response);

        adminLoginProcess.setCredential(null);
    }
}
