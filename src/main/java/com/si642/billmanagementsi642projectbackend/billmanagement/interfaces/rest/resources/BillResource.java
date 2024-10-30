package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Bank;

import java.math.BigDecimal;
import java.util.Date;

public record BillResource(
        Long id,
        String number,
        Date issueDate,
        Date dueDate,
        BigDecimal amount,
        String currency,
        String debtorName,
        String companyName
) {
}
