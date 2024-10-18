package com.si642.billmanagementsi642projectbackend.billmanagement.infrastructure.persistence.jpa.repositories;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

}
