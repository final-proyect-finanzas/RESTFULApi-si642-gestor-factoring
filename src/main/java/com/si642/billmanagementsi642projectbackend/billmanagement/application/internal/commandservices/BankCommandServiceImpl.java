package com.si642.billmanagementsi642projectbackend.billmanagement.application.internal.commandservices;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateBankCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Bank;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.BankCommandService;
import com.si642.billmanagementsi642projectbackend.billmanagement.infrastructure.persistence.jpa.repositories.BankRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankCommandServiceImpl implements BankCommandService {
    private final BankRepository bankRepository;

    public BankCommandServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    @Transactional
    public Optional<Bank> handle(CreateBankCommand command) {
        if (bankRepository.existsByName(command.name())) {
            return Optional.empty();
        }
        var bank = new Bank(command);
        try {
            bankRepository.save(bank);
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(bank);
    }
}
