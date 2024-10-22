package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateBillCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Bank;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Company;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Debtor;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.valueobjects.Currency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Bill aggregate root
 * Represents a bill - Representa una letra o factura
 */
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Represents the number of the bill. It is provided by the Company's decision
     */
    @Column(nullable = false, updatable = false)
    private String number;
    /**
     * Represents the date when the bill was emitted
     */
    @Column(nullable = false, updatable = false)
    private Date issueDate;
    /**
     * Represents the date when the bill is due
     */
    @Column(nullable = false)
    private Date dueDate;

    /**
     * Represents the amount of the bill or nominal value
     */
    @Column(nullable = false, updatable = false)
    private BigDecimal amount;
    /**
     * Represents the currency of the bill
     */
    @Column(nullable = false)
    private Currency currency;


    @ManyToOne
    @JoinColumn(name = "debtor_id")
    private Debtor debtor;


    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;


    public Bill(CreateBillCommand command, Debtor debtor, Company company) {
        this.number = command.number();
        this.issueDate = command.issueDate();
        this.dueDate = command.dueDate();
        this.amount = command.amount();
        this.currency = command.currency();
        this.debtor = debtor;
        this.company = company;
    }









}
