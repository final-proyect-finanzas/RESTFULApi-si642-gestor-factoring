package com.si642.billmanagementsi642projectbackend.billmanagement.domain.services;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Company;

import java.util.Optional;

public interface CompanyQueryService {
    Optional<Company> findById(Long id);
}
