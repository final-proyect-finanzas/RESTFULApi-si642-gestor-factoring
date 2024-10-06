package com.si642.billmanagementsi642projectbackend.billmanagement.domain.services;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateBankCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Bank;

import java.util.Optional;

public interface BankCommandService {
    Optional<Bank> handle(CreateBankCommand command);
}
