package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.controllers;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.BankCommandService;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.BankQueryService;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.BankResource;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.CreateBankResource;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.transform.BankResourceFromEntityAssembler;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.transform.CreateBankCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/banks", produces = "application/json")
@Tag(name = "Banks", description = "Bank Management Endpoints")
public class BankController {
    private final BankCommandService bankCommandService;
    private final BankQueryService bankQueryService;

    public BankController(BankCommandService bankCommandService, BankQueryService bankQueryService) {
        this.bankCommandService = bankCommandService;
        this.bankQueryService = bankQueryService;
    }

    @PostMapping("/create")
    ResponseEntity<BankResource> createBank(
            @Parameter(description = "CreateBankResource object. The 'name' field must be unique.",
                    schema = @Schema(implementation = CreateBankResource.class))
            @RequestBody CreateBankResource createBankResource) {
        var createBankCommand = CreateBankCommandFromResourceAssembler.toCommandFromResource(createBankResource);
        var bank = bankCommandService.handle(createBankCommand);
        if (bank.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var bankResource = BankResourceFromEntityAssembler.toResourceFromEntity(bank.get());
        return ResponseEntity.ok(bankResource);
    }
}
