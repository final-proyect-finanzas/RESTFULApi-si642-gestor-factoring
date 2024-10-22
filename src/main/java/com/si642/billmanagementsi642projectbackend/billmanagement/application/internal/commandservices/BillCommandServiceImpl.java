package com.si642.billmanagementsi642projectbackend.billmanagement.application.internal.commandservices;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Bill;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateBillCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateDebtorCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Company;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Debtor;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.queries.GetDebtorByName;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.*;
import com.si642.billmanagementsi642projectbackend.billmanagement.infrastructure.persistence.jpa.repositories.BillRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BillCommandServiceImpl implements BillCommandService {

    private final BillRepository billRepository;
    private final DebtorCommandService debtorCommandService;
    private final CompanyQueryService companyQueryService;
    private final DebtorQueryService debtorQueryService;

    public BillCommandServiceImpl(BillRepository billRepository, DebtorCommandService debtorCommandService,
                                  CompanyQueryService companyQueryService, DebtorQueryService debtorQueryService) {
        this.billRepository = billRepository;
        this.debtorCommandService = debtorCommandService;
        this.companyQueryService = companyQueryService;
        this.debtorQueryService = debtorQueryService;
    }

    @Override
    @Transactional
    public Optional<Bill> handle(CreateBillCommand command) {
        validateBillDoesNotExist(command.number());

        Optional<Debtor> debtor = findOrCreateDebtor(command.debtorName());
        Optional<Company> company = companyQueryService.findById(command.companyId());

            validateEntitiesExist(debtor, company);

        Bill bill = new Bill(command, debtor.get(), company.get());
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

    private void validateEntitiesExist(Optional<Debtor> debtor,Optional<Company> company) {
        if (debtor.isEmpty() || company.isEmpty()) {
            throw new IllegalArgumentException("Debtor does not exist");
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
