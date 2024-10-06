package com.si642.billmanagementsi642projectbackend.billmanagement.application.internal.queryservices;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Portfolio;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreatePortfolioCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.queries.GetPortfolioByCompanyId;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.PortfolioCommandService;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.PortfolioQueryService;
import com.si642.billmanagementsi642projectbackend.billmanagement.infrastructure.persistence.jpa.repositories.PortfolioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PortfolioQueryServiceImpl implements PortfolioQueryService {

    private final PortfolioRepository portfolioRepository;

    public PortfolioQueryServiceImpl(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }


    @Override
    public Optional<Portfolio> findById(Long id) {
        return portfolioRepository.findById(id);
    }

    @Override
    public Optional<Portfolio> handle(GetPortfolioByCompanyId query) {
        return portfolioRepository.findByCompanyId(query.companyId());
    }
}
