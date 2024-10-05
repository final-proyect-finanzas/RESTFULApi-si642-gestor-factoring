package com.si642.billmanagementsi642projectbackend.billmanagement.application.internal.outboundservices.acl;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.valueobjects.UserId;
import com.si642.billmanagementsi642projectbackend.iam.domain.model.aggregates.User;
import com.si642.billmanagementsi642projectbackend.iam.interfaces.acl.IamContextFacade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExternalIAMService {
    private final IamContextFacade iamContextFacade;

    public ExternalIAMService(IamContextFacade iamContextFacade) {
        this.iamContextFacade = iamContextFacade;
    }

    public Optional<UserId> createUser(String email, String password, List<String> roles) {
        var userId = iamContextFacade.createUser(email, password, roles);
        if (userId == 0L) return Optional.empty();
        return Optional.of(new UserId(userId));
    }
}
