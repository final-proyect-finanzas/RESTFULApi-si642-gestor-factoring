package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateWalletCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Company;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Setter
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bill> bills;

    @Temporal(TemporalType.DATE)
    private Date discountDate;

    public Wallet(CreateWalletCommand command, Company company) {
        this.discountDate = command.discountDate();
        this.company = company;
    }

    public void AddBill(Bill bill) {
        this.bills.add(bill);
    }

    public void GenerateDiscount() {
        for (Bill bill : this.bills) {
            if (bill.getDueDate().before(this.discountDate)) {
                bill.discount();
            }
        }
    }






}