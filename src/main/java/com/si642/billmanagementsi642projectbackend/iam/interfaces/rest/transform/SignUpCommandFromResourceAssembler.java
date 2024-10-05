package com.si642.billmanagementsi642projectbackend.iam.interfaces.rest.transform;



import com.si642.billmanagementsi642projectbackend.iam.domain.model.commands.SignUpCommand;
import com.si642.billmanagementsi642projectbackend.iam.domain.model.entities.Role;
import com.si642.billmanagementsi642projectbackend.iam.interfaces.rest.resources.SignUpResource;

import java.util.ArrayList;
import java.util.Optional;

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        var roles = resource.roles() != null ? resource.roles().stream().map(name -> Role.toRoleFromName(name)).toList() : new ArrayList<Role>();
        return new SignUpCommand(
                resource.username(),
                resource.password(),
                roles
        );
    }
}