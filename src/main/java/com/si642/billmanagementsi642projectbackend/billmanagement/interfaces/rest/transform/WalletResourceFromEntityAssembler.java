package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.transform;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Wallet;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.CreateWalletResource;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.WalletDiscountedResource;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.WalletResource;

import java.util.Collections;
import java.util.Optional;

public class WalletResourceFromEntityAssembler {
    public static WalletResource toResourceFromEntity(Wallet wallet) {
        return new WalletResource(wallet.getId(), wallet.getCompany().getId(),
                Optional.ofNullable(wallet.getBills())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(BillResourceFromEntityAssembler::toResourceFromEntity)
                        .toList());
    }

    public static WalletDiscountedResource toDiscountedResourceFromEntity(Wallet wallet) {
        return new WalletDiscountedResource(
                wallet.getId(),
                wallet.getCompany().getId(),
                wallet.getBank().getId(),
                wallet.getDiscountDate(),
                wallet.getInitialCost(),
                wallet.getFinalCost(),
                wallet.getNetValue(),
                wallet.getTotalAmountOfBills(),
                wallet.getTCEA().doubleValue()
        );
    }
}
