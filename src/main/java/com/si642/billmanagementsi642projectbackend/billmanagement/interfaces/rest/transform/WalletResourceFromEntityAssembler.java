package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.transform;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Wallet;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.CreateWalletResource;
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
}
