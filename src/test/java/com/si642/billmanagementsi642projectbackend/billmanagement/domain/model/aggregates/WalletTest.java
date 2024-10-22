package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateWalletCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Bank;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Company;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.valueobjects.Capitalization;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.valueobjects.TypeRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    private Wallet wallet;
    private Bank bank;
    private Company company;
    private List<com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Bill> bills;

    @BeforeEach
    void setUp() {
        company = new Company();
        bank = new Bank();
        bank.setInitialCostPerDocument(BigDecimal.valueOf(2));
        bank.setInitialPortes(BigDecimal.valueOf(10));
        bank.setFinalCommission(BigDecimal.valueOf(5));
        bank.setRate(20.0);
        bank.setDaysRate(360);
        bank.setCapitalization(Capitalization.DAILY);

        bills = new ArrayList<>();
        Bill bill1 = new Bill();
        bill1.setAmount(BigDecimal.valueOf(100));
        Bill bill2 = new Bill();
        bill2.setAmount(BigDecimal.valueOf(200));
        bills.add(bill1);
        bills.add(bill2);

        CreateWalletCommand command = new CreateWalletCommand(1L, new Date(2025, Calendar.JANUARY, 1));
        wallet = new Wallet(command, company);
        wallet.setBank(bank);
        wallet.setBills(bills);
    }

    @Test
    void calculateDaysToDiscount() {
        // Set the discount date to 9 days from now
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 9);
        wallet.setDiscountDate(calendar.getTime());

        // Calculate the days to discount
        int result = wallet.calculateDaysToDiscount();

        // Assert that the result is 9 days
        assertEquals(9, result);
    }

    @Test
    void discount() {
        wallet.discount();

        // Verify that the wallet is marked as discounted
        assertTrue(wallet.getIsDiscounted());

        // Verify that initial costs are calculated
        assertNotNull(wallet.getInitialCost());

        // Verify that final costs are calculated
        assertNotNull(wallet.getFinalCost());

        // Verify that total amount of bills is calculated
        assertNotNull(wallet.getTotalAmountOfBills());

        // Verify that net value is calculated
        assertNotNull(wallet.getNetValue());

        // Verify that amount received is calculated
        assertNotNull(wallet.getAmountReceived());

        // Verify that amount delivered is calculated
        assertNotNull(wallet.getAmountDelivered());

        // Verify that TCEA is calculated
        assertNotNull(wallet.getTCEA());
    }

    @Test
    void calculateInitialCosts() {
        wallet.calculateInitialCosts();
        assertEquals(BigDecimal.valueOf(14), wallet.getInitialCost()); // 2 * 2 + 10
    }

    @Test
    void calculateFinalCosts() {
        wallet.calculateFinalCosts();
        assertEquals(BigDecimal.valueOf(5), wallet.getFinalCost());
    }

    @Test
    void calculateTotalAmountOfBills() {
        wallet.calculateTotalAmountOfBills();
        assertEquals(BigDecimal.valueOf(300), wallet.getTotalAmountOfBills());
    }

    @Test
    void calculateTCEA() {
        wallet.setAmountDelivered(BigDecimal.valueOf(320));
        wallet.setAmountReceived(BigDecimal.valueOf(300));
        wallet.setDaysToDiscount(30);
        wallet.calculateTCEA();
        wallet.setBank(bank);
        assertNotNull(wallet.getTCEA());
    }

    @Test
    void calculateNetValue() {
        wallet.setTotalAmountOfBills(BigDecimal.valueOf(300));

        // Test for TE type rate
        bank.setTypeRate(TypeRate.TEA);
        wallet.calculateNetValue();
        BigDecimal expectedNetValueTE = wallet.getTotalAmountOfBills().multiply(BigDecimal.ONE.subtract(wallet.convertTEtoD(BigDecimal.valueOf(wallet.getBank().getRate())).divide(BigDecimal.valueOf(100))));
        assertEquals(expectedNetValueTE, wallet.getNetValue());

        // Test for TN type rate
        bank.setTypeRate(TypeRate.TNA);
        wallet.calculateNetValue();
        BigDecimal TE = wallet.convertTNtoTE(BigDecimal.valueOf(wallet.getBank().getRate()));
        BigDecimal D = wallet.convertTEtoD(TE);
        BigDecimal expectedNetValueTN = wallet.getTotalAmountOfBills().multiply(BigDecimal.ONE.subtract(D.divide(BigDecimal.valueOf(100))));
        assertEquals(expectedNetValueTN, wallet.getNetValue());
    }

    @Test
    void calculateNetValueTE() {
        wallet.setTotalAmountOfBills(BigDecimal.valueOf(300));
        wallet.calculateNetValueTE();
        assertNotNull(wallet.getNetValue());
    }

    @Test
    void calculateNetValueTN() {
        wallet.setTotalAmountOfBills(BigDecimal.valueOf(300));
        wallet.calculateNetValueTN();
        assertNotNull(wallet.getNetValue());
    }

    @Test
    void convertTEtoD() {
        BigDecimal TE = BigDecimal.valueOf(20);
        BigDecimal D = wallet.convertTEtoD(TE);
        assertNotNull(D);
    }

    @Test
    void convertTNtoTE() {
        BigDecimal TN = BigDecimal.valueOf(20);
        BigDecimal TE = wallet.convertTNtoTE(TN);
        assertNotNull(TE);
    }

    @Test
    void calculateAmountDelivered() {
        wallet.setTotalAmountOfBills(BigDecimal.valueOf(300));
        wallet.setFinalCost(BigDecimal.valueOf(50));
        wallet.calculateAmountDelivered();
        assertEquals(BigDecimal.valueOf(350), wallet.getAmountDelivered());
    }

    @Test
    void calculateAmountReceived() {
        wallet.setNetValue(BigDecimal.valueOf(300));
        wallet.setInitialCost(BigDecimal.valueOf(50));
        wallet.calculateAmountReceived();
        assertEquals(BigDecimal.valueOf(250), wallet.getAmountReceived());
    }
}