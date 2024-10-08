package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateBankCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Bank;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.valueobjects.Capitalization;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.valueobjects.TypeRate;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class BillTest {


    @Test
    public void testDiscount() {
        Bill bill = new Bill();
        bill.setNetValue(BigDecimal.valueOf(1000));
        bill.setInitialCost(BigDecimal.valueOf(50));
        bill.setFinalCost(BigDecimal.valueOf(30));
        bill.setAmount(BigDecimal.valueOf(1000));

        bill.discount();

        assertTrue(bill.getIsDiscounted());
        assertEquals(BigDecimal.valueOf(950).setScale(2, RoundingMode.HALF_UP), bill.getAmountReceived().setScale(2, RoundingMode.HALF_UP));
        assertEquals(BigDecimal.valueOf(1030).setScale(2, RoundingMode.HALF_UP), bill.getAmountDelivered().setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void testCalculateTCEA() {
        Bill bill = new Bill();
        bill.setAmountDelivered(BigDecimal.valueOf(1030));
        bill.setAmountReceived(BigDecimal.valueOf(950));
        bill.setDaysToDiscount(30);

        bill.calculateTCEA();

        assertEquals(BigDecimal.valueOf(163.85).setScale(2, RoundingMode.HALF_UP), bill.getTCEA().setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void testCalculateNetValue() {
        Bill bill = new Bill();
        Bank bank = new Bank();
        bank.setTypeRate(TypeRate.TEA);
        bank.setRate(20.0);
        bill.setBank(bank);
        bill.setAmount(BigDecimal.valueOf(1000));

        bill.calculateNetValue();

        assertEquals(BigDecimal.valueOf(833.33).setScale(2, RoundingMode.HALF_UP), bill.getNetValue().setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void testCalculateNetValueTE() {
        Bill bill = new Bill();
        Bank bank = new Bank();
        bank.setRate(20.0);
        bill.setBank(bank);
        bill.setAmount(BigDecimal.valueOf(1000));

        bill.calculateNetValueTE();

        assertEquals(BigDecimal.valueOf(833.33).setScale(2, RoundingMode.HALF_UP), bill.getNetValue().setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void testCalculateNetValueTNA() {
        Bill bill = new Bill();
        Bank bank = new Bank();
        bank.setRate(20.0);
        bank.setTypeRate(TypeRate.TNA);
        bank.setCapitalization(Capitalization.DAILY);
        bill.setBank(bank);
        bill.setAmount(BigDecimal.valueOf(1000));

        //TEA = 22.133 %
        //D = 18.12204 %
        //Net Value = 1000 (1 - 18.12204%)

        bill.calculateNetValueTNA();

        assertEquals(BigDecimal.valueOf(818.78).setScale(2, RoundingMode.HALF_UP), bill.getNetValue().setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void TestConvertTNtoTE(){
        Bill bill = new Bill();
        bill.setBank(new Bank(new CreateBankCommand("bankName", 20.0, TypeRate.TNA, 52, Capitalization.MONTHLY )));

        var result = bill.convertTNtoTE(BigDecimal.valueOf(bill.getBank().getRate()));

        assertEquals(result.setScale(2, RoundingMode.HALF_UP), BigDecimal.valueOf(21.94).setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void testConvertTEtoD() {
        Bill bill = new Bill();
        BigDecimal TE = BigDecimal.valueOf(20.0); // Ejemplo de tasa efectiva
        BigDecimal expectedD = BigDecimal.valueOf(16.67).setScale(2, RoundingMode.HALF_UP); // Valor esperado de D

        BigDecimal result = bill.convertTEtoD(TE);

        assertEquals(expectedD, result.setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void TestCalculateDRate(){

    }


}