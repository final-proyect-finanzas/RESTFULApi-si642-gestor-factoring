package com.si642.billmanagementsi642projectbackend.billmanagement.domain.services;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Debtor;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.queries.GetDebtorByName;

import java.util.Optional;

public interface DebtorQueryService {
    Optional<Debtor> handle(GetDebtorByName query);
}
