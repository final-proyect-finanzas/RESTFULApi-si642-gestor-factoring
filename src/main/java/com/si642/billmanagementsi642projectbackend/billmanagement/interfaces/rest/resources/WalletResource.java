package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources;

import java.util.List;

public record WalletResource(
        Long id,
        Long companyId,
        List<BillResource> bills
) {
}
