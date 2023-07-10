package com.binark.school.usermanagement.service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.ScriptAssert;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.jose.jwk.JSONWebKeySet;
import org.keycloak.jose.jwk.JWK;
import org.keycloak.jose.jwk.JWKParser;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.security.PublicKey;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class AccountRoleResolver {

    @Value("${spring.security.oauth2.client.provider.keycloak.jwkSetUri}")
    private String REALM_CERTS_URI;

    private static final String RESOURCE_CLAIM = "sukulu-um";

    private static final String SCHOOLS_CLAIM = "schools";

    public Set<String> getAccountSchools(String token) {
        AccessToken accessToken = extractAccessTokenFrom(token);
        List<String> schools = (List<String>) accessToken.getOtherClaims().get(SCHOOLS_CLAIM);

        Set<String> schoolSet = new HashSet<>();

        if (schools != null) {
            schoolSet.addAll(schools);
        }

        return schoolSet;
    }

    public Set<String> getAccountRoles(String token) {

        AccessToken accessToken = extractAccessTokenFrom(token);
        AccessToken.Access resourceAccess = accessToken.getResourceAccess(RESOURCE_CLAIM);

        if (resourceAccess == null) {
            return Set.of();
        }

        return resourceAccess.getRoles();
    }

    private PublicKey retrievePublicKeyFromCertsEndpoint() {

        try {
            ObjectMapper om = new ObjectMapper();
            JSONWebKeySet jwks = om.readValue(new URL(REALM_CERTS_URI).openStream(), JSONWebKeySet.class);
            JWK jwk = jwks.getKeys()[0];
            return JWKParser.create(jwk).toPublicKey();
        } catch (Exception e) {
            log.error("Exception", e);
        }

        return null;
    }

    private AccessToken extractAccessTokenFrom(String token) {

        if (token == null) {
            return null;
        }

        try {
            PublicKey publicKey = retrievePublicKeyFromCertsEndpoint();
            TokenVerifier tokenVerifier = TokenVerifier.create(token, AccessToken.class);
            return (AccessToken) tokenVerifier.publicKey(publicKey).verify().getToken();
        } catch (VerificationException e) {
            log.debug("VerificationException: ", e);
            return null;
        }
    }
}
