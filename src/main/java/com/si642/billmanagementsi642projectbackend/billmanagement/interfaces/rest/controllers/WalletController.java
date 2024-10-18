package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.controllers;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.WalletCommandService;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.CreateWalletResource;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.WalletResource;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.transform.CreateWalletCommandFromResourceAssembler;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.transform.WalletResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/wallets", produces = "application/json")
@Tag(name = "Wallets", description = "Wallet Management Endpoints")
public class WalletController {
    private final WalletCommandService walletCommandService;

    public WalletController(WalletCommandService walletCommandService) {
        this.walletCommandService = walletCommandService;
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
}
