package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.transform;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateCompanyCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.CreateCompanyResource;

public class CreateCompanyCommandFromResourceAssembler {
    public static CreateCompanyCommand toCommandFromResource(CreateCompanyResource resource) {
        return new CreateCompanyCommand(
                resource.name(),
                resource.address(),
                resource.email(),
                resource.phone(),
                resource.username(),
                resource.password(),
                resource.roles()
        );
    }
}
