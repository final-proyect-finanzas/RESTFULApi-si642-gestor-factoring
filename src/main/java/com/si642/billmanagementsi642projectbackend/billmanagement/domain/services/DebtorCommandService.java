package com.si642.billmanagementsi642projectbackend.billmanagement.domain.services;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateDebtorCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Debtor;

import java.util.Optional;

public interface DebtorCommandService {
    Optional<Debtor> handle(CreateDebtorCommand command);
}
