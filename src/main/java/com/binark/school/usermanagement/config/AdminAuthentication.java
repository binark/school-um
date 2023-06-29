package com.binark.school.usermanagement.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AdminAuthentication implements Authentication {

    private List<GrantedAuthority> authorities;

    private String username;

    private boolean authenticated;

    private String password;

    public AdminAuthentication(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AdminAuthentication(String username, Collection<String> auth) {
        this.authorities = auth.stream()
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList());
        this.username = username;
        this.authenticated = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return this.username;
    }
}
