package com.si642.billmanagementsi642projectbackend.billmanagement.application.internal.queryservices;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Bill;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.queries.GetBillsByCompanyId;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.BillQueryService;
import com.si642.billmanagementsi642projectbackend.billmanagement.infrastructure.persistence.jpa.repositories.BillRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillQueryServiceImpl implements BillQueryService {
    private final BillRepository billRepository;

    public BillQueryServiceImpl(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Override
    public Optional<List<Bill>> handle(GetBillsByCompanyId query) {
        return billRepository.findBillsByCompanyId(query.companyId());
    }
}
