package com.si642.billmanagementsi642projectbackend.iam.domain.model.commands;



import com.si642.billmanagementsi642projectbackend.iam.domain.model.entities.Role;

import java.util.List;
import java.util.Optional;

public record SignUpCommand(String username, String password, List<Role> roles) {
}