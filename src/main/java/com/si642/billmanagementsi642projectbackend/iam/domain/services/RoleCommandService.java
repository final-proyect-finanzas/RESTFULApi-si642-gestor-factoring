package com.si642.billmanagementsi642projectbackend.iam.domain.services;


import com.si642.billmanagementsi642projectbackend.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}
