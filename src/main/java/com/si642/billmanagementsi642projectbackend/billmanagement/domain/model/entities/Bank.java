package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateBankCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.valueobjects.Capitalization;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.valueobjects.TypeRate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Represents a bank
 * This class represents the bank to discount the bill
 * It has a tax to be applied to the amount of the bill
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    /*
    * Tax to be applied to the amount of the bill
    * It is a percentage value
     */
    private Double rate;

    private TypeRate typeRate;

    /*
    * Days rate to be applied the rate
    * Example: TNA 360 days
     */
    private Integer daysRate;

    private Capitalization capitalization;

    private BigDecimal initialCostPerDocument;

    private BigDecimal initialPortes;

    private BigDecimal finalCommission;

    public Bank(CreateBankCommand command) {
        this.name = command.name();
        this.rate = command.rate();
        this.typeRate = command.typeRate();
        this.daysRate = command.daysRate();
        this.capitalization = command.capitalization();
        this.initialCostPerDocument = command.initialCostPerDocument();
        this.initialPortes = command.initialPortes();
        this.finalCommission = command.finalCommission();
    }

}
