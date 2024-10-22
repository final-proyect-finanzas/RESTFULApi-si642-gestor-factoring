package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.controllers;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.AddBillToWalletCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.DiscountWalletCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.WalletCommandService;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.WalletQueryService;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.CreateUpdateWalletResource;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.CreateWalletResource;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.WalletDiscountedResource;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.WalletResource;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.transform.CreateUpdateWalletCommandFromResourceAssembler;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.transform.CreateWalletCommandFromResourceAssembler;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.transform.WalletResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/wallets", produces = "application/json")
@Tag(name = "Wallets", description = "Wallet Management Endpoints")
public class WalletController {
    private final WalletCommandService walletCommandService;
    private final WalletQueryService walletQueryService;

    public WalletController(WalletCommandService walletCommandService, WalletQueryService walletQueryService) {
        this.walletCommandService = walletCommandService;
        this.walletQueryService = walletQueryService;
    }

    @PostMapping
    public ResponseEntity<WalletResource> createWallet(@RequestBody CreateWalletResource createWalletResource) {
        var command = CreateWalletCommandFromResourceAssembler.toCommandFromResource(createWalletResource);
        var wallet = walletCommandService.handle(command);
        if (wallet.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(WalletResourceFromEntityAssembler.toResourceFromEntity(wallet.get()));
    }

    @PutMapping("/{walletId}")
    public ResponseEntity<WalletResource> updateWallet(@RequestBody CreateUpdateWalletResource createUpdateWalletResource, @PathVariable Long walletId) {
        var command = CreateUpdateWalletCommandFromResourceAssembler.toCommandFromResource(createUpdateWalletResource, walletId);
        var wallet = walletCommandService.handle(command);
        if (wallet.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(WalletResourceFromEntityAssembler.toResourceFromEntity(wallet.get()));
    }


    @PutMapping("/{walletId}/bills")
    public ResponseEntity<Long> addBillToWallet(@PathVariable Long walletId, @RequestBody Long billId) {
        var command = new AddBillToWalletCommand(walletId, billId);
        var bill = walletCommandService.handle(command);
        if (bill.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bill.get());
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<WalletResource> getWallet(@PathVariable Long walletId) {
        var wallet = walletQueryService.findById(walletId);
        if (wallet.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(WalletResourceFromEntityAssembler.toResourceFromEntity(wallet.get()));
    }

    @PutMapping("/discount/{walletId}")
    public ResponseEntity<WalletDiscountedResource> discountWallet(@PathVariable Long walletId, @RequestBody DiscountWalletCommand command) {
        var commandDiscount = new DiscountWalletCommand(walletId, command.bankId());
        var wallet = walletCommandService.handle(commandDiscount);
        if (wallet.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(WalletResourceFromEntityAssembler.toDiscountedResourceFromEntity(wallet.get()));
    }





}
