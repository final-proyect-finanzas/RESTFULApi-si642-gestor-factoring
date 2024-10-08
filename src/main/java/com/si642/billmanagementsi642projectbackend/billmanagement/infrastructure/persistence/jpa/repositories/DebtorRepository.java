package com.si642.billmanagementsi642projectbackend.billmanagement.infrastructure.persistence.jpa.repositories;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Debtor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DebtorRepository extends JpaRepository<Debtor, Long> {
    boolean existsById(Long id);
    boolean existsByProfileName (String profileName);
    Optional<Debtor> findByProfileName(String profileName);
}
