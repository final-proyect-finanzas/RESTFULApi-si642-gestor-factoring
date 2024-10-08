package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands;

public record CreateDebtorCommand(
        String name,
        String email,
        String phone,
        String address
) {
}
