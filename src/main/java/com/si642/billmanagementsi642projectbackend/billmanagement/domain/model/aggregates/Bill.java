package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateBillCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Bank;
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

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @Column(nullable = false)
    private Boolean isDiscounted = false;

    private BigDecimal amountReceived = BigDecimal.ZERO;

    private BigDecimal amountDelivered = BigDecimal.ZERO;

    private Integer daysToDiscount = 0;

    private BigDecimal TCEA = BigDecimal.ZERO;



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
        this.daysToDiscount = this.calculateDaysToDiscount();
    }

    public int calculateDaysToDiscount() {
        return (int) ((this.dueDate.getTime() - this.issueDate.getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * This method represents the discount of the bill
     *
     */
    public void discount(){
        this.isDiscounted = true;
        this.amountReceived = this.netValue.subtract(this.initialCost);
        this.amountDelivered = this.amount.add(this.finalCost);

    }

    /**
     * This method represents the value of TCEA of this bill.
     * TCEA = ((ValueDelivered / ValueReceived)^(360/daysToDiscount) - 1)*100
     */
    public void calculateTCEA() {
        BigDecimal valueDelivered = this.amountDelivered;
        BigDecimal valueReceived = this.amountReceived;
        BigDecimal daysToDiscount = BigDecimal.valueOf(this.daysToDiscount);
        BigDecimal TCEA = valueDelivered.divide(valueReceived, 10, BigDecimal.ROUND_HALF_UP)
                .pow( BigDecimal.valueOf(360).divide(daysToDiscount, 10, BigDecimal.ROUND_HALF_UP).intValue())
                .subtract(BigDecimal.ONE)
                .multiply(BigDecimal.valueOf(100));
        this.TCEA = TCEA;
    }

    /**
     * Calculate the net value of the bill
     * Net value = amount * ( 1 - D%)
     */
    public void calculateNetValue() {
        String typeRatePrefix = this.bank.getTypeRate().name().substring(0, 2);
        if(typeRatePrefix.equals("TE")) {
            this.calculateNetValueTE();
        } else {
            this.calculateNetValueTNA();    
        }
    }

    public void calculateNetValueTE() {
        BigDecimal D = this.convertTEtoD(BigDecimal.valueOf(this.bank.getRate()));
        this.netValue = this.amount.multiply(BigDecimal.ONE.subtract(D.divide(BigDecimal.valueOf(100))));
    }


    public void calculateNetValueTNA() {
        BigDecimal TE = this.convertTNtoTE(BigDecimal.valueOf(this.bank.getRate()));
        BigDecimal D = this.convertTEtoD(TE);
        this.netValue = this.amount.multiply(BigDecimal.ONE.subtract(D.divide(BigDecimal.valueOf(100))));
    }



    /**
     * Calculate the discount rate "D%" = TE / (1 + TE)
     * The input is the TE rate
     * Return the D rate
     */
    public BigDecimal convertTEtoD(BigDecimal TE) {
        double rate = TE.doubleValue() / 100;
        double result = rate / (1 + rate);
        return BigDecimal.valueOf(result * 100);
    }

    /**
     * Calculate the discount rate "TE" = TN / n
     * TE = (1 + TN/m)^n - 1
     * m = Period that capitalization is of the TN
     * n = Period that capitalization is of the TE
     * Bank have this data:
     * TN = 20.0 %
     * Capitalization = DAILY 360
     * n = if the TE is same to m in this case
     * TE = (1 + 20.0/360)^360 - 1 * 100 = 22.13%
     */
    public BigDecimal convertTNtoTE(BigDecimal TN) {
        double rate = TN.doubleValue() / 100;
        int period = this.bank.getCapitalization().getPeriod();
        double result = Math.pow(1 + rate / period, period) - 1;
        return BigDecimal.valueOf(result * 100);
    }


}
