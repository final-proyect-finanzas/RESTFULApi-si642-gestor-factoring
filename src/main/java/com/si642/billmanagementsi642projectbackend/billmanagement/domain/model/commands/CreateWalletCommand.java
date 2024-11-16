package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.valueobjects.Currency;

import java.util.Date;

public record CreateWalletCommand(
        Long companyId,
        Date discountDate,
        Currency currency
) {
}
