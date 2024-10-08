package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.transform;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateBankCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.valueobjects.Capitalization;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.valueobjects.TypeRate;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.CreateBankResource;

public class CreateBankCommandFromResourceAssembler {
    public static CreateBankCommand toCommandFromResource(CreateBankResource createBankResource) {
        return new CreateBankCommand(
                createBankResource.name(),
                createBankResource.rate(),
                TypeRate.valueOf(createBankResource.typeRate()),
                createBankResource.daysRate(),
                Capitalization.valueOf(createBankResource.capitalization())
        );
    }
}
