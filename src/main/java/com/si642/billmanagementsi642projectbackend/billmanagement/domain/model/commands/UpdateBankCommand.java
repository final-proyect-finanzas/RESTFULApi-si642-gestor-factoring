package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.valueobjects.Capitalization;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.valueobjects.TypeRate;

/**
 * Command to update a bank
 * Represents the intention to update a bank.
 * Change only the rate,  typeRate, daysRate and capitalization
 */
public record UpdateBankCommand(
        Long id,
        Double rate,
        TypeRate typeRate,
        Integer daysRate,
        Capitalization capitalization
) {
}
