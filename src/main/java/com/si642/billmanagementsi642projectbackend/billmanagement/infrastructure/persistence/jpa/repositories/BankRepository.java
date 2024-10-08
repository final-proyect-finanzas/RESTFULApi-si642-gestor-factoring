package com.si642.billmanagementsi642projectbackend.billmanagement.infrastructure.persistence.jpa.repositories;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
    boolean existsByName(String name);
}
