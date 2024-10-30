package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources;

import java.math.BigDecimal;

public record CreateBankResource(
        String name,
        Double rate,
        String typeRate,
        Integer daysRate,
        String capitalization,
        Double initialCostPerDocument,
        Double initialPortes,
        Double finalCommission
) {
}
