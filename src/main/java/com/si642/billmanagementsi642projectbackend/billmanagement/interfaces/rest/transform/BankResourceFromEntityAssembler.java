package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.transform;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities.Bank;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.BankResource;

public class BankResourceFromEntityAssembler {
    public static BankResource toResourceFromEntity(Bank bank) {
        return new BankResource(
                bank.getId(),
                bank.getName(),
                bank.getRate(),
                bank.getTypeRate().toString(),
                bank.getDaysRate(),
                bank.getCapitalization().name()
        );
    }
}
