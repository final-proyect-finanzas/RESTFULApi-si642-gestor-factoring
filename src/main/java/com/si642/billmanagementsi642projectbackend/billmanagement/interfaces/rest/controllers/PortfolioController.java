package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.controllers;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.PortfolioCommandService;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.services.PortfolioQueryService;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.CreatePortfolioResource;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources.PortfolioResource;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.transform.CreatePortfolioCommandFromResourceAssembler;
import com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.transform.PortfolioResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/portfolios", produces = "application/json")
@Tag(name = "Portfolios", description = "Portfolio Management Endpoints")
public class PortfolioController {
    private final PortfolioCommandService portfolioCommandService;
    private final PortfolioQueryService portfolioQueryService;

    public PortfolioController(PortfolioCommandService portfolioCommandService, PortfolioQueryService portfolioQueryService) {
        this.portfolioCommandService = portfolioCommandService;
        this.portfolioQueryService = portfolioQueryService;
    }

    @PostMapping
    public ResponseEntity<PortfolioResource> createPortfolio(@RequestBody CreatePortfolioResource createPortfolioResource){
        var createPortfolioCommand = CreatePortfolioCommandFromResourceAssembler.toCommandFromResource(createPortfolioResource);
        var portfolioId = portfolioCommandService.handle(createPortfolioCommand).get().getId();
        if (portfolioId == 0L){
            return ResponseEntity.badRequest().build();
        }
        var portfolio = portfolioQueryService.findById(portfolioId);
        if (portfolio.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        var portfolioResource = PortfolioResourceFromEntityAssembler.toResourceFromEntity(portfolio.get());
        return ResponseEntity.ok(portfolioResource);
    }
}
