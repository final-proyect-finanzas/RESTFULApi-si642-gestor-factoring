package com.si642.billmanagementsi642projectbackend.billmanagement.application.internal.commandservices;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Bill;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Portfolio;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateBillCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateDebtorCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Bank;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Company;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Debtor;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.*;
import com.si642.billmanagementsi642projectbackend.billmanagement.infrastructure.persistence.jpa.repositories.BillRepository;
import com.si642.billmanagementsi642projectbackend.billmanagement.infrastructure.persistence.jpa.repositories.CompanyRepository;
import com.si642.billmanagementsi642projectbackend.billmanagement.infrastructure.persistence.jpa.repositories.DebtorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BillCommandServiceImpl implements BillCommandService {

    private final BillRepository billRepository;
    private final DebtorCommandService debtorCommandService;
    private final BankQueryService bankQueryService;
    private final PortfolioQueryService portfolioQueryService;
    private final CompanyQueryService companyQueryService;

    public BillCommandServiceImpl(BillRepository billRepository, DebtorCommandService debtorCommandService, BankQueryService bankQueryService, PortfolioQueryService portfolioQueryService, CompanyQueryService companyQueryService) {
        this.billRepository = billRepository;
        this.debtorCommandService = debtorCommandService;
        this.bankQueryService = bankQueryService;
        this.portfolioQueryService = portfolioQueryService;
        this.companyQueryService = companyQueryService;
    }

    @Override
    @Transactional
    public Optional<Bill> handle(CreateBillCommand command) {
        if (billRepository.existsByNumber(command.number())) {
            throw new IllegalArgumentException("Bill with number " + command.number() + " already exists");
        }
        CreateDebtorCommand debtorCommand = new CreateDebtorCommand(command.debtorName(), "", "", "");
        Optional<Debtor> debtor = debtorCommandService.handle(debtorCommand);
        Optional<Bank> bank = bankQueryService.findById(command.bankId());
        Optional<Portfolio> portfolio = portfolioQueryService.findById(command.portfolioId());
        if (debtor.isEmpty() || bank.isEmpty() || portfolio.isEmpty() ) {
            throw new IllegalArgumentException("Debtor, Bank, Portfolio or Company not found");
        }
        var bill = new Bill(command, debtor.get(), portfolio.get(), bank.get());
        try {
            return Optional.of(billRepository.save(bill));
        } catch (Exception e) {
            return Optional.empty();
        }

    }
}
