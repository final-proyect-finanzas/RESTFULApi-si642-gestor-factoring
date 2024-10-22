package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands;

public record DiscountWalletCommand(
        Long walletId,
        Long bankId
) {
}
