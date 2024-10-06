package com.si642.billmanagementsi642projectbackend.billmanagement.application.internal.queryservices;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Bank;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.BankQueryService;
import com.si642.billmanagementsi642projectbackend.billmanagement.infrastructure.persistence.jpa.repositories.BankRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankQueryServiceImpl implements BankQueryService {
    private final BankRepository bankRepository;

    public BankQueryServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public Optional<Bank> findById(Long id) {
        return bankRepository.findById(id);
    }
}
