package com.binark.school.usermanagement.service.iam;

import com.binark.school.usermanagement.entity.Account;
import com.binark.school.usermanagement.entity.Owner;
import com.binark.school.usermanagement.service.auth.OAuth2Manager;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;

@Service("iamOwner")
@RequiredArgsConstructor
public class OwnerManager implements IamManager {

    private final OAuth2Manager oAuth2Manager;

    @Override
    public String createUser(Account account) throws IllegalArgumentException{

        if (!(account instanceof Owner)) {
            throw new IllegalArgumentException("instance mismatch");
        }

        Owner owner = (Owner)account;

        // Get realm resource to perform iam operation
        RealmResource resource = this.oAuth2Manager.getRealmResource();

        // Get iam user (the owner)
        UserRepresentation user = this.getIamOwner(owner);

        UsersResource usersResource = resource.users();

        // Create iam account for the owner
        Response response = usersResource.create(user);

//        System.out.printf("Repsonse: %s %s%n", response.getStatus(), response.getStatusInfo());
//        System.out.println(response.getLocation());
        // Get the id of the newly created owner
        String userId = CreatedResponseUtil.getCreatedId(response);

//        System.out.printf("User created with userId: %s%n", userId);

//        CredentialRepresentation passwordCredential = this.getPassword();
//
//        UserResource userResource = usersResource.get(userId);
//
//        // Set password credential
//        userResource.resetPassword(passwordCredential);

        return userId;

    }

    private UserRepresentation getIamOwner(Owner owner) {

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(false);
        user.setEmail(owner.getEmail());
        user.setUsername(owner.getEmail());
        user.setFirstName(owner.getFirstname());
        user.setLastName(owner.getLastname());
        user.setAttributes(Collections.singletonMap("slug", Arrays.asList(owner.getSlug())));

        return user;
    }

    private CredentialRepresentation getPassword() {

        CredentialRepresentation passwordCredential = new CredentialRepresentation();
        passwordCredential.setTemporary(false);
        passwordCredential.setType(CredentialRepresentation.PASSWORD);
        passwordCredential.setValue("test");

        return passwordCredential;
    }
}
