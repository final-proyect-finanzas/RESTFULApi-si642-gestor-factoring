package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.valueobjects.Currency;

import java.math.BigDecimal;
import java.util.Date;

public record CreateBillCommand(
        String number,
        Date issueDate,
        Date dueDate,
        BigDecimal amount,
        Currency currency,
        String debtorName,
        Long companyId
) {
}
