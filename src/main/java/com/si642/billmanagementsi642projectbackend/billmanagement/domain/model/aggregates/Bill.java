package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateBillCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Bank;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Debtor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;
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

    @Column(nullable = false, updatable = false)
    private String number;
    @Column(nullable = false, updatable = false)
    private Date issueDate;
    @Column(nullable = false)
    private Date dueDate;
    @Column(nullable = false)
    private Date discountDate;
    @Column(nullable = false, updatable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "debtor_id")
    private Debtor debtor;

    @OneToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    public Bill(CreateBillCommand createBillCommand){
        this.number = createBillCommand.number();
        this.issueDate = createBillCommand.issueDate();
        this.dueDate = createBillCommand.dueDate();
        this.discountDate = createBillCommand.discountDate();
        this.amount = createBillCommand.amount();
        this.currency = createBillCommand.currency();
        this.debtor = createBillCommand.debtor();
        this.portfolio = createBillCommand.portfolio();
    }





}
