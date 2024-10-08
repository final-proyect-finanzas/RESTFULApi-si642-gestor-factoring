package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.transform;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreatePortfolioCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.CreatePortfolioResource;

public class CreatePortfolioCommandFromResourceAssembler {
    public static CreatePortfolioCommand toCommandFromResource(CreatePortfolioResource resource) {
        return new CreatePortfolioCommand(
                resource.companyId()
        );
    }
}
