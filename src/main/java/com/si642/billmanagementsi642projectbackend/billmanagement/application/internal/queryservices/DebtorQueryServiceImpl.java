package com.si642.billmanagementsi642projectbackend.billmanagement.application.internal.queryservices;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Debtor;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.queries.GetDebtorByName;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.DebtorQueryService;
import com.si642.billmanagementsi642projectbackend.billmanagement.infrastructure.persistence.jpa.repositories.DebtorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DebtorQueryServiceImpl implements DebtorQueryService {
    private final DebtorRepository debtorRepository;

    public DebtorQueryServiceImpl(DebtorRepository debtorRepository) {
        this.debtorRepository = debtorRepository;
    }

    @Override
    public Optional<Debtor> handle(GetDebtorByName query) {
        return debtorRepository.findByProfileName(query.name());
    }
}
