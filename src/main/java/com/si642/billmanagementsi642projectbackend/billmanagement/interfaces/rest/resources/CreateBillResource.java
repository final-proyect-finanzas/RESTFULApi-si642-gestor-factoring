package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.Date;

public record CreateBillResource(
        String number,
        Date issueDate,
        Date dueDate,
        BigDecimal amount,
        String currency,
        String debtorName,
        Long companyId
) {
}
