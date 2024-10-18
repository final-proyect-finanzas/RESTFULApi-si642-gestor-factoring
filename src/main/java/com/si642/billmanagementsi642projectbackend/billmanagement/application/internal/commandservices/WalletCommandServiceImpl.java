package com.si642.billmanagementsi642projectbackend.billmanagement.application.internal.commandservices;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Wallet;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.AddBillToWalletCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateWalletCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.UpdateWalletCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.BillQueryService;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.CompanyQueryService;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.WalletCommandService;
import com.si642.billmanagementsi642projectbackend.billmanagement.infrastructure.persistence.jpa.repositories.BillRepository;
import com.si642.billmanagementsi642projectbackend.billmanagement.infrastructure.persistence.jpa.repositories.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletCommandServiceImpl implements WalletCommandService {
    private final WalletRepository walletRepository;
    private final CompanyQueryService companyQueryService;
    private final BillRepository billRepository;

    public WalletCommandServiceImpl(WalletRepository walletRepository, CompanyQueryService companyQueryService, BillQueryService billQueryService, BillRepository billRepository) {
        this.walletRepository = walletRepository;
        this.companyQueryService = companyQueryService;
        this.billRepository = billRepository;
    }
    @Override
    public Optional<Wallet> handle(CreateWalletCommand command) {
        var company = companyQueryService.findById(command.companyId());
        if (company.isEmpty()) {
            throw new IllegalArgumentException("Company not found");
        }
        var wallet = new Wallet(command, company.get());
        try {
            walletRepository.save(wallet);
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(wallet);
    }

    @Override
    public Optional<Wallet> handle(UpdateWalletCommand command) {
        var walletOptional = walletRepository.findById(command.walletId());
        if (walletOptional.isEmpty()) {
            return Optional.empty();
        }
        var wallet = walletOptional.get();
        wallet.setDiscountDate(command.discountDate());
        try {
            walletRepository.save(wallet);
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(wallet);
    }

    @Override
    public Optional<Long> handle(AddBillToWalletCommand command) {
        var walletOptional = walletRepository.findById(command.walletId());
        var billOptional = billRepository.findById(command.billId());
        if (walletOptional.isEmpty() || billOptional.isEmpty()) {
            throw new IllegalArgumentException("Wallet or Bill not found");
        }
        var wallet = walletOptional.get();
        wallet.AddBill(billOptional.get());
        try {
            walletRepository.save(wallet);
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(wallet.getId());
    }
}
