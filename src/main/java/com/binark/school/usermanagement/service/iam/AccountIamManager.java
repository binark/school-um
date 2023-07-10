package com.binark.school.usermanagement.service.iam;

import com.binark.school.usermanagement.entity.Account;
import com.binark.school.usermanagement.service.auth.OAuth2Manager;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Primary
public class AccountIamManager implements IamManager{

    private final OAuth2Manager oAuth2Manager;

    @Override
    public String createUser(Account account) throws IllegalArgumentException {
        return null;
    }

    public void addUserSchool(String userId, String schoolSlug) {
        UserResource userResource = getUserResource(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        Map<String, List<String>> attributes = userRepresentation.getAttributes();

        if (attributes == null) {
            attributes = new HashMap<>();
        }

        List<String> schools = attributes.get("schools");

        if (schools == null) {
            schools = Arrays.asList(schoolSlug);
        } else if (!schools.contains(schoolSlug)) {
            schools.add(schoolSlug);
        }

        attributes.put("schools", schools);

        userRepresentation.setAttributes(attributes);

        userResource.update(userRepresentation);
    }

    public void activateAccount(String userId, String password) {
        UserResource userResource = this.getUserResource(userId);
        CredentialRepresentation passwordRepresentation = this.getPassword(password);

        this.setPassword(userResource, passwordRepresentation);
        this.enableUserAccount(userResource);
    }

    private UserResource getUserResource(String userId) {

        RealmResource resource = this.oAuth2Manager.getRealmResource();
        UsersResource usersResource = resource.users();

        return usersResource.get(userId);
    }

    private CredentialRepresentation getPassword(String password) {

        CredentialRepresentation passwordCredential = new CredentialRepresentation();
        passwordCredential.setTemporary(false);
        passwordCredential.setType(CredentialRepresentation.PASSWORD);
        passwordCredential.setValue(password);

        return passwordCredential;
    }

    private void setPassword(UserResource userResource, CredentialRepresentation password) {
        userResource.resetPassword(password);
    }

    private void enableUserAccount(UserResource userResource) {
        UserRepresentation userRepresentation = userResource.toRepresentation();
        userRepresentation.setEnabled(true);

        userResource.update(userRepresentation);
    }
}
