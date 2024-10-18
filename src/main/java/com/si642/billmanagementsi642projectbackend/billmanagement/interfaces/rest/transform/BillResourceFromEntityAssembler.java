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
                bill.getDiscountDate(),
                bill.getAmount(),
                bill.getCurrency().toString(),
                bill.getInitialCost(),
                bill.getFinalCost(),
                bill.getNetValue(),
                bill.getBank(),
                bill.getDebtor().getProfile().getName(),
                bill.getPortfolio().getCompany().getProfile().getName(),
                bill.getDaysToDiscount()
        );
    }
}
