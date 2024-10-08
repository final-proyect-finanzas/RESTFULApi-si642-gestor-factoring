package com.si642.billmanagementsi642projectbackend.billmanagement.infrastructure.persistence.jpa.repositories;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Bill;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    Optional<Portfolio> findByCompanyId(Long companyId);
}
