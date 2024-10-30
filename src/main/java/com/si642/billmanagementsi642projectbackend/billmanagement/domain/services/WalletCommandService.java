package com.si642.billmanagementsi642projectbackend.billmanagement.domain.services;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Wallet;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.AddBillToWalletCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateWalletCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.DiscountWalletCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.UpdateWalletCommand;

import java.util.Optional;

public interface WalletCommandService {
    Optional<Wallet> handle(CreateWalletCommand command);

    Optional<Wallet> handle(UpdateWalletCommand command);

    Optional<Long> handle(AddBillToWalletCommand command);

    Optional<Wallet> handle(DiscountWalletCommand command);
}
