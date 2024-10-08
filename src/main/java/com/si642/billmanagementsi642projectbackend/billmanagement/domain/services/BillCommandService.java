package com.si642.billmanagementsi642projectbackend.billmanagement.domain.services;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Bill;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateBillCommand;

import java.util.Optional;

public interface BillCommandService {
    Optional<Bill> handle(CreateBillCommand command);
}
