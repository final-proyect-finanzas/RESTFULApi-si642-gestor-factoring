package com.si642.billmanagementsi642projectbackend.iam.domain.services;



import com.si642.billmanagementsi642projectbackend.iam.domain.model.aggregates.User;
import com.si642.billmanagementsi642projectbackend.iam.domain.model.queries.GetAllUsersQuery;
import com.si642.billmanagementsi642projectbackend.iam.domain.model.queries.GetUserByIdQuery;
import com.si642.billmanagementsi642projectbackend.iam.domain.model.queries.GetUserByUsernameQuery;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<User> handle(GetAllUsersQuery query);
    Optional<User> handle(GetUserByIdQuery query);
    Optional<User> handle(GetUserByUsernameQuery query);

}
