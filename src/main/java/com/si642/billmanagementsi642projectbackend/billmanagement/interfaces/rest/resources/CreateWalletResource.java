package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources;

import java.util.Date;

public record CreateWalletResource(
        Long companyId,
        Date discountDate
) {
}