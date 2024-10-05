package com.si642.billmanagementsi642projectbackend.iam.interfaces.rest.transform;


import com.si642.billmanagementsi642projectbackend.iam.domain.model.commands.SignInCommand;
import com.si642.billmanagementsi642projectbackend.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource signInResource) {
        return new SignInCommand(signInResource.username(), signInResource.password());
    }
}
