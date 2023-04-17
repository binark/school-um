package com.binark.school.usermanagement.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.vault.authentication.UsernamePasswordAuthentication;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AdminAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        log.debug("******************************** try to authenticate user");
        return new AdminUserDetail("username", "test", new ArrayList<>());
    }

//    @Value("${admin.email}")
//    private String email;
//
//    @Value("${admin.password}")
//    private String password;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        System.out.println("******************************* admin auth provider");
//        AdminAuthentication adminAuthentication = (AdminAuthentication) authentication;
//        if (!(adminAuthentication.getPrincipal().equals(email) && adminAuthentication.getCredentials().equals(password))) {
//            throw new AuthenticationServiceException("Email or password error");
//        }
//        List<String> auths = new ArrayList<>();
//        auths.add("SUPER");
//        return new UsernamePasswordAuthenticationToken("Kenany", "auths", new ArrayList<>());
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(UsernamePasswordAuthentication.class);
//    }
}
