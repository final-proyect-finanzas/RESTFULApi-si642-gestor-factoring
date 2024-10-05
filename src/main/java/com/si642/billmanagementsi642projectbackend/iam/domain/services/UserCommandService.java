package com.si642.billmanagementsi642projectbackend.iam.domain.services;


import com.si642.billmanagementsi642projectbackend.iam.domain.model.aggregates.User;
import com.si642.billmanagementsi642projectbackend.iam.domain.model.commands.SignInCommand;
import com.si642.billmanagementsi642projectbackend.iam.domain.model.commands.SignUpCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface UserCommandService {
    Optional<ImmutablePair<User, String>> handle(SignInCommand command);
    Optional<User> handle(SignUpCommand command);


}
