package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources;

public record CompanyResource(
        Long companyId,
        String name,
        String address,
        String email,
        String phone
) {
}
