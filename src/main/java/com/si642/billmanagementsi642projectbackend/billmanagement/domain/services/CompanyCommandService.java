package com.si642.billmanagementsi642projectbackend.billmanagement.domain.services;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateCompanyCommand;

public interface CompanyCommandService {
    Long handle(CreateCompanyCommand command);
}
