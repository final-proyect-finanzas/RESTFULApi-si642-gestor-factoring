package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.transform;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Company;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.CompanyResource;

public class CompanyResourceFromEntityAssembler {
    public static CompanyResource toResourceFromEntity(Company company) {
        return new CompanyResource(
                company.getId(),
                company.getProfile().getName(),
                company.getProfile().getAddress(),
                company.getProfile().getEmail(),
                company.getProfile().getPhone()
        );
    }
}
