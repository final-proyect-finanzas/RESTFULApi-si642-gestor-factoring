package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreatePortfolioCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Company;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "portfolio")
    private List<Bill> bills;

    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Portfolio(Company company) {
        this.company = company;
    }


}
