package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.controllers;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.CompanyCommandService;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.CompanyQueryService;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.CompanyResource;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.CreateCompanyResource;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.transform.CompanyResourceFromEntityAssembler;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.transform.CreateCompanyCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/companies", produces = "application/json")
@Tag(name = "Companies", description = "Company Management Endpoints")
public class CompanyController {
    private final CompanyCommandService companyCommandService;
    private final CompanyQueryService companyQueryService;

    public CompanyController(CompanyCommandService companyCommandService, CompanyQueryService companyQueryService) {
        this.companyCommandService = companyCommandService;
        this.companyQueryService = companyQueryService;
    }

    @PostMapping
    public ResponseEntity<CompanyResource> createCompany(@RequestBody CreateCompanyResource createCompanyResource){
        var createCompanyCommand = CreateCompanyCommandFromResourceAssembler.toCommandFromResource(createCompanyResource);
        var companyId = companyCommandService.handle(createCompanyCommand);
        if (companyId == 0L){
            return ResponseEntity.badRequest().build();
        }
        var company = companyQueryService.findById(companyId);
        if (company.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        var companyResource = CompanyResourceFromEntityAssembler.toResourceFromEntity(company.get());
        return ResponseEntity.ok(companyResource);
    }
}
