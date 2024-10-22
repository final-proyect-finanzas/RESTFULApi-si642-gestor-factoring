package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.transform;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.UpdateWalletCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.CreateUpdateWalletResource;

public class CreateUpdateWalletCommandFromResourceAssembler{
    public static UpdateWalletCommand toCommandFromResource(CreateUpdateWalletResource createUpdateWalletResource, Long walletId) {
        return new UpdateWalletCommand(
                walletId,
                createUpdateWalletResource.discountDate()
        );
    }
}


