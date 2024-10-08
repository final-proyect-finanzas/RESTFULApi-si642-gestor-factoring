package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.valueobjects;

import lombok.Getter;

@Getter
public enum Capitalization {
    DAILY(365),
    MONTHLY(12),
    YEARLY(1),
    NONE(0);

    private final int period;

    Capitalization(int period) {
        this.period = period;
    }

}