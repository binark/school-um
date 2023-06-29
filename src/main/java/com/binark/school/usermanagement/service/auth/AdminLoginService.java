package com.binark.school.usermanagement.service.auth;

import com.binark.school.usermanagement.config.AdminLoginProcess;
import com.binark.school.usermanagement.exception.AuthenticationException;
import com.binark.school.usermanagement.exception.UserNotFoundException;
import com.binark.school.usermanagement.publisher.AdminLoginFirstStepPublisher;
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
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Perform the admin login process.
 */
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

    /**
     * Start admin login process by checking email, and generate the one time password
     * @param email The email to check
     * @return {@link String} The generated password TODO should not return anything
     * @throws AuthenticationException
     */
    public String startLoginProcess(String email) throws AuthenticationException {

        // If email is null or it doesn't match with admin email
        if (email == null || !email.equals(adminKey)) {
            log.warn("Someone tried to connect to admin account with wrong mail:  " + email);
            // Send real admin notification. He should know that someone tried to spoil his account
            this.wrongEmailPublisher.publsh(adminKey, email);
            //throw new AuthenticationException(email);
            // The returns nothing
            return "";
        }

        // generate the one time password if the email match
        String password = UUID.randomUUID().toString();

        // Set password value to a session scope bean
        this.adminLoginProcess.setCredential(password.toUpperCase());

        // Send admin email with the generated password.
        publisher.publsh(email);

        return password;
    }

    /**
     * @see this#processLogin(String, String, boolean, HttpServletRequest, HttpServletResponse)
     */
    public void processLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) throws UserNotFoundException, AuthenticationException {
        this.processLogin(username, password, false, request, response);
    }

    /**
     * Perform admin login
     * @param username The admin email
     * @param password The admin password (one time generated password)
     * @param rememberMe
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @throws UserNotFoundException If username (email) doesn't match
     * @throws AuthenticationException If password doesn't match or there is not saved one time password
     */
    public void processLogin(String username, String password, boolean rememberMe, HttpServletRequest request, HttpServletResponse response) throws UserNotFoundException, AuthenticationException {

        log.debug("**************** Login as admin");

        // Check if saved password exists
        if (this.adminLoginProcess.getCredential() == null) {
            // If there is not saved password
            throw new AuthenticationException("Authentification failed");
        }

        // Compare the password with the saved password
        if (password == null || !password.equals(this.adminLoginProcess.getCredential())) {
            // If the password doesn't match
            throw new AuthenticationException("Authentification failed");
        }

        // Perform Spring security context login
        SecurityContextHolderStrategy holderStrategy = SecurityContextHolder.getContextHolderStrategy();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken
                .unauthenticated(username, password);
        Authentication authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        holderStrategy.setContext(context);
        strategy.saveContext(context, request, response);

        // Delete the saved password
        adminLoginProcess.setCredential(null);
    }
}
