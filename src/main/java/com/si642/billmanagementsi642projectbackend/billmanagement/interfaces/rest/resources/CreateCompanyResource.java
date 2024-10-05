package com.si642.billmanagementsi642projectbackend.billmanagement.interfaces.rest.resources;

import java.util.List;

public record CreateCompanyResource (
        String name,
        String address,
        String email,
        String phone,
        String username,
        String password,
        List<String> roles
){}
