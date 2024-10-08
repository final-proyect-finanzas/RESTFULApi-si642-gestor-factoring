package com.si642.billmanagementsi642projectbackend.billmanagement.domain.services;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Portfolio;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreatePortfolioCommand;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface PortfolioCommandService {
    Optional<Portfolio> handle(CreatePortfolioCommand createPortfolioCommand);
}
