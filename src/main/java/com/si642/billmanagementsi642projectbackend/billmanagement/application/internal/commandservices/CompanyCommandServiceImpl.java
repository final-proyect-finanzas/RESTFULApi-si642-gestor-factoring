package com.si642.billmanagementsi642projectbackend.billmanagement.application.internal.commandservices;

import com.si642.billmanagementsi642projectbackend.billmanagement.application.internal.outboundservices.acl.ExternalIAMService;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateCompanyCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Company;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.CompanyCommandService;
import com.si642.billmanagementsi642projectbackend.billmanagement.infrastructure.persistence.jpa.repositories.CompanyRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CompanyCommandServiceImpl implements CompanyCommandService {

    private final CompanyRepository companyRepository;
    private final ExternalIAMService externalIAMService;

    public CompanyCommandServiceImpl(CompanyRepository companyRepository, ExternalIAMService externalIAMService) {
        this.companyRepository = companyRepository;
        this.externalIAMService = externalIAMService;
    }

    @Override
    @Transactional
    public Long handle(CreateCompanyCommand command) {
        if (companyRepository.existsByName(command.name())) {
            throw new IllegalArgumentException("Company with same name already exists");
        }
        var userId = externalIAMService.createUser(command.username(), command.password(), command.roles());
        if (userId.isEmpty()) {
            throw new IllegalArgumentException("Error while creating user in IAM");
        }
        var company = new Company(command, userId.get());
        try {
            companyRepository.save(company);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving company: " + e.getMessage());
        }
        return company.getId();
    }
}
