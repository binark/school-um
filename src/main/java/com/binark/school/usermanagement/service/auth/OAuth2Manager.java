package com.binark.school.usermanagement.service.auth;

import com.binark.school.usermanagement.controller.response.UserResponse;
import com.binark.school.usermanagement.exception.AuthenticationException;
import com.binark.school.usermanagement.exception.UserNotFoundException;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.NotAuthorizedException;
import java.util.List;

@Component
public class OAuth2Manager {

    @Value("${resource.server.url}")
    private String serverUrl;

    @Value("${client.registration.name}")
    private String clientRegistrationName;

    @Value("${spring.security.oauth2.client.registration.keycloak.clientId}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.clientSecret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.provider.keycloak.tokenUri}")
    private String tokenUri;

    @Value("${resource.server.realm}")
    private String realm;

    @Autowired
    private RestTemplate restTemplate;

    public TokenResponse getAccessToken(String username, String password) throws AuthenticationException {
        return this.getAccessToken(username, password, false);
    }

    public TokenResponse getAccessToken(String username, String password, Boolean rememberMe) throws AuthenticationException {

        Keycloak client = this.getOAuth2ClientInstance(username, password);

        TokenManager tokenManager = client.tokenManager();

        try {
      //      tokenManager.setMinTokenValidity(rememberMe ? TokenValidaty.REMEMBERME : TokenValidaty.NORMAL);
            AccessTokenResponse accessToken = tokenManager.getAccessToken();

            System.out.println("access token:  " + accessToken);

            return TokenResponse.builder()
                    .accessToken(accessToken.getToken())
                    .notBeforePolicy(accessToken.getNotBeforePolicy())
                    .tokenType(accessToken.getTokenType())
                    .expiresIn(accessToken.getExpiresIn())
                    .refreshToken(accessToken.getRefreshToken())
                    .refreshExpriesIn(accessToken.getRefreshExpiresIn())
                    .build();

        }catch (NotAuthorizedException nae) {
            System.out.println(nae.getMessage());
            nae.printStackTrace();
            throw new AuthenticationException("Nom d'utilisateur ou mot de passe incorrect");
        }

    }

    public UserResponse getUserInfo(String accessToken, String username) throws UserNotFoundException {

        Keycloak oAuth2ClientInstance = this.getOAuth2ClientInstance(accessToken);

        System.out.println("oAuth2ClientInstance = " + oAuth2ClientInstance);

        TokenManager tokenManager = oAuth2ClientInstance.tokenManager();

        AccessTokenResponse token = tokenManager.getAccessToken();

        System.out.println("token.getToken() = " + token.getToken());

        List<UserRepresentation> users = oAuth2ClientInstance.realm(realm).users().list(); //.search(username);

        if (users.isEmpty()) {
            throw new UserNotFoundException();
        }

        UserRepresentation userRepresentation = users.get(0);

        UserResponse user = UserResponse.builder().email(userRepresentation.getEmail())
                .username(userRepresentation.getUsername())
                .firstname(userRepresentation.getFirstName())
                .lastname(userRepresentation.getLastName())
                .requiredActions(userRepresentation.getRequiredActions())
                .build();

        return user;

    }

    public Keycloak getOAuth2ClientInstance(String username, String password) {
        return KeycloakBuilder
                .builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
             //   .grantType("password")
                .username(username)
                .password(password)
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();
    }

    public Keycloak getOAuth2ClientInstance(String accessToken) {
        return KeycloakBuilder
                .builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(clientId)
               // .grantType("password")
                .clientSecret(clientSecret)
                .authorization(accessToken)
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();
    }

    public RealmResource getRealmResource() {
        return this.getOAuth2ClientInstance("sukuluadmin", "admin").realm(realm);
    }
}
