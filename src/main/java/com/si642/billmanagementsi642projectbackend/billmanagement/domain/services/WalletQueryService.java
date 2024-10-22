package com.si642.billmanagementsi642projectbackend.billmanagement.domain.services;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Wallet;

import java.util.Optional;

public interface WalletQueryService {
    Optional<Wallet> findById(Long walletId);
}
