package com.binark.school.usermanagement.config;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Value("${resource.server.url}")
    private String serverUrl;
    @Value("${resource.server.realm}")
    private String realm;
    @Value("${spring.security.oauth2.client.registration.keycloak.clientId}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.keycloak.clientSecret}")
    private String clientSecret;

    @Bean
    public KeycloakBuilder keycloakBuilder() {

        return KeycloakBuilder
                .builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
              //  .grantType("client_credentials")
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build());
    }
}
