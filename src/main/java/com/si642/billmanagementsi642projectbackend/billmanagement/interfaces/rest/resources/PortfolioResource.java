package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Bill;

import java.util.List;

public record PortfolioResource(
        Long id,
        List<Bill> bills,
        Long companyId
) {
}
