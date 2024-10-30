package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.Date;

public record WalletDiscountedResource(
        Long id,
        Long companyId,
        Long bankId,
        Date discountDate,
        BigDecimal initialCost,
        BigDecimal finalCost,
        BigDecimal netValue,
        BigDecimal totalAmountOfBills,
        Double TCEA
) {
}
