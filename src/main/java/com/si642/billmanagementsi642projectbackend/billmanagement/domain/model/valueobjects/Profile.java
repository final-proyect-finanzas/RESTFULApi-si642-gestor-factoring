package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class Profile {
    private String name;
    private String address;
    private String email;
    private String phone;

    public Profile(String name, String address, String email, String phone) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }
}
