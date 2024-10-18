package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Bank;

import java.math.BigDecimal;
import java.util.Date;

public record BillResource(
        Long id,
        String number,
        Date issueDate,
        Date dueDate,
        Date discountDate,
        BigDecimal amount,
        String currency,
        BigDecimal initialCost,
        BigDecimal finalCost,
        BigDecimal netValue,
        Bank bank,
        String debtorName,
        String companyName,
        Integer daysToDiscount
) {
}
