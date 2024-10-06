package com.si642.billmanagementsi642projectbackend.billmanagement.application.internal.commandservices;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateDebtorCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Debtor;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.DebtorCommandService;
import com.si642.billmanagementsi642projectbackend.billmanagement.infrastructure.persistence.jpa.repositories.DebtorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DebtorCommandServiceImpl implements DebtorCommandService {
    private final DebtorRepository debtorRepository;

    public DebtorCommandServiceImpl(DebtorRepository debtorRepository) {
        this.debtorRepository = debtorRepository;
    }

    @Override
    @Transactional
    public Optional<Debtor> handle(CreateDebtorCommand command) {
        if (debtorRepository.existsByProfileName(command.name())) {
            throw new IllegalArgumentException("Debtor with name " + command.name() + " already exists");
        }
        var debtor = new Debtor(command.name());
        try {
            return Optional.of(debtorRepository.save(debtor));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
