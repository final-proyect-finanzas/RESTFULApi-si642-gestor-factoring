package com.si642.billmanagementsi642projectbackend.iam.interfaces.rest.resources;// SignUpResource


import java.util.List;
import java.util.Optional;

public record SignUpResource(String username, String password, List<String> roles) {
}