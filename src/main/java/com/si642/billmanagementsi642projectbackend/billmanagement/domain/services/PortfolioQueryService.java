package com.si642.billmanagementsi642projectbackend.billmanagement.domain.services;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Portfolio;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.queries.GetPortfolioByCompanyId;

import java.util.Optional;

public interface PortfolioQueryService {
    Optional<Portfolio> findById(Long id);

    Optional<Portfolio> handle(GetPortfolioByCompanyId query);
}
