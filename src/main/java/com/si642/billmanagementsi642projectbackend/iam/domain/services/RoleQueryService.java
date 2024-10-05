package com.si642.billmanagementsi642projectbackend.iam.domain.services;




import com.si642.billmanagementsi642projectbackend.iam.domain.model.entities.Role;
import com.si642.billmanagementsi642projectbackend.iam.domain.model.queries.GetAllRolesQuery;
import com.si642.billmanagementsi642projectbackend.iam.domain.model.queries.GetRoleByNameQuery;

import java.util.List;
import java.util.Optional;

public interface RoleQueryService {
    List<Role> handle(GetAllRolesQuery query);
    Optional<Role> handle(GetRoleByNameQuery query);
}
