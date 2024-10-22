package com.si642.billmanagementsi642projectbackend.billmanagement.domain.services;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Bill;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.queries.GetBillsByCompanyId;

import java.util.List;
import java.util.Optional;

public interface BillQueryService {
    Optional<List<Bill>> handle(GetBillsByCompanyId query);
}
