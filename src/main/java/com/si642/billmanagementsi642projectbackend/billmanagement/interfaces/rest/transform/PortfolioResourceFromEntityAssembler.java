package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.transform;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Portfolio;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.PortfolioResource;

public class PortfolioResourceFromEntityAssembler {
    public static PortfolioResource toResourceFromEntity(Portfolio entity) {
        return new PortfolioResource(
                entity.getId(),
                entity.getBills(),
                entity.getCompany().getId()
        );
    }
}
