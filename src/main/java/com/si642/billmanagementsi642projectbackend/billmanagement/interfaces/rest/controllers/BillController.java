package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.controllers;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.queries.GetBillsByCompanyId;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.BillCommandService;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.BillQueryService;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.BillResource;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.CreateBillResource;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.transform.BillResourceFromEntityAssembler;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.transform.CreateBillCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/v1/bills", produces = "application/json")
@Tag(name = "Bills", description = "Bill Management Endpoints - Letras y facturas")
public class BillController {
    private final BillCommandService billCommandService;
    private final BillQueryService billQueryService;

    public BillController(BillCommandService billCommandService, BillQueryService billQueryService) {
        this.billCommandService = billCommandService;
        this.billQueryService = billQueryService;
    }

    @PostMapping("/create")
    ResponseEntity<BillResource> createBill(@RequestBody CreateBillResource createBillResource) {
        var createBillCommand = CreateBillCommandFromResourceAssembler.toCommandFromResource(createBillResource);
        var bill = billCommandService.handle(createBillCommand);
        if (bill.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var billResource = BillResourceFromEntityAssembler.toResourceFromEntity(bill.get());
        return ResponseEntity.ok(billResource);
    }

    @GetMapping("/getByCompanyId/{companyId}")
    ResponseEntity<List<BillResource>> getBillsByPortfolioId(@PathVariable Long companyId) {
        var bills = billQueryService.handle(new GetBillsByCompanyId(companyId));
        if (bills.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var billsResponse = bills.get().stream()
                .map(BillResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(billsResponse);
    }

}
