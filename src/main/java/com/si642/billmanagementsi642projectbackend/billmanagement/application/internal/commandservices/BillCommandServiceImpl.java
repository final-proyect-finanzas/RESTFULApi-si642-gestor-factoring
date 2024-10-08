package com.si642.billmanagementsi642projectbackend.billmanagement.application.internal.commandservices;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Bill;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Portfolio;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateBillCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateDebtorCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Bank;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Company;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Debtor;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.queries.GetDebtorByName;
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
    private final DebtorQueryService debtorQueryService;

    public BillCommandServiceImpl(BillRepository billRepository, DebtorCommandService debtorCommandService,
                                  BankQueryService bankQueryService, PortfolioQueryService portfolioQueryService,
                                  CompanyQueryService companyQueryService, DebtorQueryService debtorQueryService) {
        this.billRepository = billRepository;
        this.debtorCommandService = debtorCommandService;
        this.bankQueryService = bankQueryService;
        this.portfolioQueryService = portfolioQueryService;
        this.companyQueryService = companyQueryService;
        this.debtorQueryService = debtorQueryService;
    }

    @Override
    @Transactional
    public Optional<Bill> handle(CreateBillCommand command) {
        validateBillDoesNotExist(command.number());

        Optional<Debtor> debtor = findOrCreateDebtor(command.debtorName());
        Optional<Bank> bank = bankQueryService.findById(command.bankId());
        Optional<Portfolio> portfolio = portfolioQueryService.findById(command.portfolioId());

        validateEntitiesExist(debtor, bank, portfolio);

        Bill bill = new Bill(command, debtor.get(), portfolio.get(), bank.get());
        return saveBill(bill);
    }

    private void validateBillDoesNotExist(String billNumber) {
        if (billRepository.existsByNumber(billNumber)) {
            throw new IllegalArgumentException("Bill with number " + billNumber + " already exists");
        }
    }

    private Optional<Debtor> findOrCreateDebtor(String debtorName) {
        return debtorQueryService.handle(new GetDebtorByName(debtorName))
                .or(() -> debtorCommandService.handle(new CreateDebtorCommand(debtorName, "", "", "")));
    }

    private void validateEntitiesExist(Optional<Debtor> debtor, Optional<Bank> bank, Optional<Portfolio> portfolio) {
        if (debtor.isEmpty() || bank.isEmpty() || portfolio.isEmpty()) {
            throw new IllegalArgumentException("Debtor, Bank, or Portfolio not found");
        }
    }

    private Optional<Bill> saveBill(Bill bill) {
        try {
            return Optional.of(billRepository.save(bill));
        } catch (Exception e) {
            throw new IllegalArgumentException("Error saving bill", e);
        }
    }
}
