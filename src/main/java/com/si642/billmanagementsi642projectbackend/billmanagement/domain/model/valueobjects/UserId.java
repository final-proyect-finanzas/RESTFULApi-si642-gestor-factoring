package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record UserId(
        Long userId
) {
}
