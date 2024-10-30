package com.si642.billmanagementsi642projectbackend.billmanagement.domain.services;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Bank;

import java.util.List;
import java.util.Optional;

public interface BankQueryService {
    Optional<Bank> findById(Long id);
    Optional<List<Bank>> findAll();
}
