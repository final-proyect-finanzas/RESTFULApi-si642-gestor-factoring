package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateBillCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Bank;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Debtor;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.valueobjects.Currency;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.valueobjects.TypeRate;
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
     * Represents the date when the discount is applied
     */
    @Column(nullable = false)
    private Date discountDate;
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

    /**
     * Represents the initial costs of the bill
     * Like the cost of the paper, ink, etc
     */
    private BigDecimal initialCost;

    /**
     * Represents the final costs of the bill
     * Like the cost of the paper, ink, etc
     */
    private BigDecimal finalCost;

    /**
     * Represents the net value of the bill
     */
    private BigDecimal netValue;

    @ManyToOne
    @JoinColumn(name = "debtor_id")
    private Debtor debtor;

    @OneToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    public Bill(CreateBillCommand command, Debtor debtor, Portfolio portfolio, Bank bank) {
        this.number = command.number();
        this.issueDate = command.issueDate();
        this.dueDate = command.dueDate();
        this.discountDate = command.discountDate();
        this.amount = command.amount();
        this.currency = command.currency();
        this.debtor = debtor;
        this.portfolio = portfolio;
        this.bank = bank;
        this.initialCost = command.initialCosts();
        this.finalCost = command.finalCosts();
        this.calculateNetValue();
    }

    private void calculateNetValue() {
        this.netValue = this.amount.subtract(this.initialCost).subtract(this.finalCost);
    }

    /**
     * Calculate the discount rate "D%" = TE / (1 + TE)
     */
    private void calculateDrate() {
        if(this.bank.getTypeRate() == TypeRate.TNA) {
            this.netValue = this.netValue.multiply(BigDecimal.valueOf(this.bank.getRate()));
        }
    }

    /**
     * Calculate the discount rate "TE" = TN / n
     * TE = (1 + TN/m)^n - 1
     * m = Period that capitalization is of the TN
     * n = Period that capitalization is of the TE
     */
    //TODO: Review this formulas
    private BigDecimal convertTNtoTE(BigDecimal TE) {
        return BigDecimal.valueOf((Math.pow(1 + this.bank.getRate() / this.bank.getDaysRate(), this.bank.getDaysRate()) - 1)*100);
    }





}
