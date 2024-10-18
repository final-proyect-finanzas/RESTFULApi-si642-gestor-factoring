package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands;

public record AddBillToWalletCommand(
        Long walletId,
        Long billId
) {
}
