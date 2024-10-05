package com.si642.billmanagementsi642projectbackend.iam.interfaces.rest.transform;


import com.si642.billmanagementsi642projectbackend.iam.domain.model.entities.Role;
import com.si642.billmanagementsi642projectbackend.iam.interfaces.rest.resources.RoleResource;

public class RoleResourceFromEntityAssembler {
    public static RoleResource toResourceFromEntity(Role role) {
        return new RoleResource(role.getId(), role.getStringName());
    }
}
