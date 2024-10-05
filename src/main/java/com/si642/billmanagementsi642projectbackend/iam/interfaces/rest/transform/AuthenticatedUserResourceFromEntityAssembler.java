package com.si642.billmanagementsi642projectbackend.iam.interfaces.rest.transform;


import com.si642.billmanagementsi642projectbackend.iam.domain.model.aggregates.User;
import com.si642.billmanagementsi642projectbackend.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(user.getId(), user.getUsername(), token);
    }
}
