package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.transform;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateBillCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.valueobjects.Currency;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.CreateBillResource;

public class CreateBillCommandFromResourceAssembler {
    public static CreateBillCommand toCommandFromResource(CreateBillResource resource) {
        return new CreateBillCommand(
                resource.number(),
                resource.issueDate(),
                resource.dueDate(),
                resource.discountDate(),
                resource.amount(),
                Currency.valueOf(resource.currency()),
                resource.debtorName(),
                resource.portfolioId(),
                resource.bankId(),
                resource.initialCost(),
                resource.finalCost()
        );
    }
}
