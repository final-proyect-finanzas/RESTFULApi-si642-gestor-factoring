package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Company;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Portfolio {

    @Id
    private Long id;

    @OneToMany(mappedBy = "portfolio")
    private List<Bill> bills;

    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
