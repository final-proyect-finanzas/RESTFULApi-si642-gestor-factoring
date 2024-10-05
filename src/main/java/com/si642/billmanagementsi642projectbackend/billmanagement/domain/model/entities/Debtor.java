package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.valueobjects.Profile;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Debtor {

    @Id
    private Long id;

    @Embedded
    private Profile profile;

    public Debtor(String name, String address, String email, String phone) {
        this.profile = new Profile(name, address, email, phone);
    }

    public Debtor() {
        this.profile = new Profile("", "", "", "");
    }
}