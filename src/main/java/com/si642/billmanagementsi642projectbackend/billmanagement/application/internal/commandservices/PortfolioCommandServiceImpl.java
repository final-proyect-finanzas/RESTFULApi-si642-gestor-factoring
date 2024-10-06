package com.si642.billmanagementsi642projectbackend.billmanagement.application.internal.commandservices;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Portfolio;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreatePortfolioCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.PortfolioCommandService;
import com.si642.billmanagementsi642projectbackend.billmanagement.infrastructure.persistence.jpa.repositories.CompanyRepository;
import com.si642.billmanagementsi642projectbackend.billmanagement.infrastructure.persistence.jpa.repositories.PortfolioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PortfolioCommandServiceImpl implements PortfolioCommandService {

    private final PortfolioRepository portfolioRepository;
    private final CompanyRepository companyRepository;

    public PortfolioCommandServiceImpl(PortfolioRepository portfolioRepository, CompanyRepository companyRepository) {
        this.portfolioRepository = portfolioRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    @Transactional
    public Optional<Portfolio> handle(CreatePortfolioCommand createPortfolioCommand) {
        var company = companyRepository.findById(createPortfolioCommand.companyId());
        if (company.isEmpty()) {
            return Optional.empty();
        }
        var portfolio = new Portfolio(company.get());
        try {
            portfolioRepository.save(portfolio);
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(portfolio);
    }
}
