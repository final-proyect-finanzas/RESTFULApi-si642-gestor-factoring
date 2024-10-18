package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.Date;

public record CreateBillResource(
        String number,
        Date issueDate,
        Date dueDate,
        Date discountDate,
        BigDecimal amount,
        String currency,
        BigDecimal initialCost,
        BigDecimal finalCost,
        String debtorName,
        Long portfolioId,
        Long bankId,
        Integer daysToDiscount
) {
}
