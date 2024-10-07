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
    public void TestConvertTNtoTE(){
        Bill bill = new Bill();
        bill.setBank(new Bank(new CreateBankCommand("bankName", 20.0, TypeRate.TNA, 52, Capitalization.MONTHLY )));

        var result = bill.convertTNtoTE(BigDecimal.valueOf(bill.getBank().getRate()));

        assertEquals(result.setScale(2, RoundingMode.HALF_UP), BigDecimal.valueOf(21.94).setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void TestCalculateDRate(){

    }


}