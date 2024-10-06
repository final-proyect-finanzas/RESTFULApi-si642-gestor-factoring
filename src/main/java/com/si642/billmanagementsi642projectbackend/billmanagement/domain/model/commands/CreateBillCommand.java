package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Portfolio;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Debtor;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

public record CreateBillCommand(
        String number,
        Date issueDate,
        Date dueDate,
        Date discountDate,
        BigDecimal amount,
        Currency currency,
        Long debtorId,
        Long portfolioId,
        Long bankId,
        Long companyId,
        BigDecimal initialCosts,
        BigDecimal finalCosts
) {
}
