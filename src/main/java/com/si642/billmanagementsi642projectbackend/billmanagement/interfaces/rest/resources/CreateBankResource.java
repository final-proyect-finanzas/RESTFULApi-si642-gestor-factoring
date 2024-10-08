package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources;

public record CreateBankResource(
        String name,
        Double rate,
        String typeRate,
        Integer daysRate,
        String capitalization
) {
}
