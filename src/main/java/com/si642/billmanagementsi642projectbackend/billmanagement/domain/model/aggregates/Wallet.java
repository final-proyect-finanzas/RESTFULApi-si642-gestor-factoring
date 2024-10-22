package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateWalletCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Bank;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Company;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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

    private BigDecimal initialCost;
    private BigDecimal finalCost;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @Column(nullable = false)
    private Boolean isDiscounted = false;

    private BigDecimal netValue;
    private BigDecimal totalAmountOfBills = BigDecimal.ZERO;
    private BigDecimal amountReceived = BigDecimal.ZERO;
    private BigDecimal amountDelivered = BigDecimal.ZERO;
    private BigDecimal TCEA = BigDecimal.ZERO;
    private Integer daysToDiscount = 0;

    public Wallet(CreateWalletCommand command, Company company) {
        this.discountDate = command.discountDate();
        this.company = company;
    }

    public int calculateDaysToDiscount() {
        long diff = this.discountDate.getTime() - new Date().getTime();
        this.daysToDiscount = (int) Math.ceil(diff / (1000.0 * 60 * 60 * 24));
        return (int) Math.ceil(diff / (1000.0 * 60 * 60 * 24));
    }

    public void addBill(Bill bill) {
        this.bills.add(bill);
        bill.setWallet(this);
    }

    public void discount() {
        this.calculateDaysToDiscount();
        this.calculateInitialCosts();
        this.calculateFinalCosts();
        this.calculateTotalAmountOfBills();
        this.calculateNetValue();
        this.calculateAmountReceived();
        this.calculateAmountDelivered();

        this.calculateTCEA();

        System.out.println("Discounted");
        this.isDiscounted = true;
    }

    public void calculateAmountDelivered() {
        this.amountDelivered = this.totalAmountOfBills.add(this.finalCost);
    }

    public void calculateAmountReceived() {
        this.amountReceived = this.netValue.subtract(this.initialCost);
    }

    public void calculateInitialCosts() {
        BigDecimal initialCostPerDocument = this.bank.getInitialCostPerDocument();
        BigDecimal totalInitialCost = initialCostPerDocument.multiply(BigDecimal.valueOf(this.bills.size()));
        this.initialCost = totalInitialCost.add(bank.getInitialPortes());
    }

    public void calculateFinalCosts() {
        this.finalCost = bank.getFinalCommission();
    }


    public void calculateTotalAmountOfBills() {
        BigDecimal totalAmount = BigDecimal.ZERO;
        if (this.bills != null) {
            for (Bill bill : this.bills) {
                totalAmount = totalAmount.add(bill.getAmount());
            }
        }
        this.totalAmountOfBills = totalAmount;
    }


    public void calculateTCEA() {
        if (this.amountReceived.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Amount received cannot be zero for TCEA calculation.");
        }
        BigDecimal valueDelivered = this.amountDelivered;
        BigDecimal valueReceived = this.amountReceived;
        BigDecimal daysRate = BigDecimal.valueOf(bank.getDaysRate());
        BigDecimal exponent = BigDecimal.valueOf(360).divide(daysRate, 10, BigDecimal.ROUND_HALF_UP);
        BigDecimal TCEA = valueDelivered.divide(valueReceived, 10, BigDecimal.ROUND_HALF_UP)
                .pow(exponent.intValue())
                .subtract(BigDecimal.ONE)
                .multiply(BigDecimal.valueOf(100));
        this.TCEA = TCEA;
    }

    public void calculateNetValue() {
        String typeRatePrefix = this.bank.getTypeRate().name().substring(0, 2);
        if (typeRatePrefix.equals("TE")) {
            this.calculateNetValueTE();
        } else {
            this.calculateNetValueTN();
        }
    }

    public void calculateNetValueTE() {
        BigDecimal D = this.convertTEtoD(BigDecimal.valueOf(this.bank.getRate()));
        this.netValue = this.totalAmountOfBills.multiply(BigDecimal.ONE.subtract(D.divide(BigDecimal.valueOf(100))));
    }

    public void calculateNetValueTN() {
        BigDecimal TE = this.convertTNtoTE(BigDecimal.valueOf(this.bank.getRate()));
        BigDecimal D = this.convertTEtoD(TE);
        this.netValue = this.totalAmountOfBills.multiply(BigDecimal.ONE.subtract(D.divide(BigDecimal.valueOf(100))));
    }

    public BigDecimal convertTEtoD(BigDecimal TE) {
        double rate = TE.doubleValue() / 100;
        double result = rate / (1 + rate);
        return BigDecimal.valueOf(result * 100);
    }

    public BigDecimal convertTNtoTE(BigDecimal TN) {
        double rate = TN.doubleValue() / 100;
        int period = this.bank.getCapitalization().getPeriod();
        double result = Math.pow(1 + rate / period, period) - 1;
        return BigDecimal.valueOf(result * 100);
    }


}