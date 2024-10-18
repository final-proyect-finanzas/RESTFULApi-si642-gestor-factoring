package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands;

import java.util.Date;

public record UpdateWalletCommand(
        Long walletId,
        Date discountDate
) {
}
