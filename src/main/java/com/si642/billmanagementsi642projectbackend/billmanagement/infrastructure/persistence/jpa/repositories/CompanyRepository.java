package com.si642.billmanagementsi642projectbackend.billmanagement.infrastructure.persistence.jpa.repositories;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Company c WHERE c.profile.name = :name")
    boolean existsByName(@Param("name") String name);
}