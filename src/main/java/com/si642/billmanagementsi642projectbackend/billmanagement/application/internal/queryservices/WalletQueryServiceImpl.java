package com.si642.billmanagementsi642projectbackend.billmanagement.application.internal.queryservices;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Wallet;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.WalletQueryService;
import com.si642.billmanagementsi642projectbackend.billmanagement.infrastructure.persistence.jpa.repositories.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WalletQueryServiceImpl implements WalletQueryService {
    private final WalletRepository walletRepository;

    public WalletQueryServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public Optional<Wallet> findById(Long walletId) {
        return walletRepository.findById(walletId);
    }

    @Override
    public Optional<List<Wallet>> findAllByCompanyId(Long companyId) {
        return walletRepository.findAllByCompanyId(companyId);
    }
}
