package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.valueobjects.Capitalization;

public record BankResource(
        Long id,
        String name,
        Double rate,
        String typeRate,
        Integer daysRate,
        String capitalization
) {
}
