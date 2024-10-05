package com.si642.billmanagementsi642projectbackend.iam.interfaces.rest.transform;


import com.si642.billmanagementsi642projectbackend.iam.domain.model.aggregates.User;
import com.si642.billmanagementsi642projectbackend.iam.domain.model.entities.Role;
import com.si642.billmanagementsi642projectbackend.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user) {
        var roles = user.getRoles().stream().map(Role::getStringName).toList();
        return new UserResource(user.getId(),
                user.getUsername(),
                roles);
    }
}
