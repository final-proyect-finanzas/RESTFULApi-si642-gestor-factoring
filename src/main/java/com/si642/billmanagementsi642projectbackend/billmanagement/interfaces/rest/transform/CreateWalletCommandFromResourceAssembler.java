package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.transform;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateWalletCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.CreateWalletResource;

public class CreateWalletCommandFromResourceAssembler {
    public static CreateWalletCommand toCommandFromResource(CreateWalletResource createWalletResource) {
        return new CreateWalletCommand(
                createWalletResource.companyId(),
                createWalletResource.discountDate()
        );
    }
}
