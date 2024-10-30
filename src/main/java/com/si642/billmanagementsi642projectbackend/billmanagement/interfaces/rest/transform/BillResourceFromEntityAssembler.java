package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.transform;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Bill;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.BillResource;

public class BillResourceFromEntityAssembler {
    public static BillResource toResourceFromEntity(Bill bill) {
        return new BillResource(
                bill.getId(),
                bill.getNumber(),
                bill.getIssueDate(),
                bill.getDueDate(),
                bill.getAmount(),
                bill.getCurrency().toString(),
                bill.getDebtor().getProfile().getName(),
                bill.getCompany().getProfile().getName()
        );
    }
}
