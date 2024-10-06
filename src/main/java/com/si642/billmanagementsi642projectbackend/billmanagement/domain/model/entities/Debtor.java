package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateDebtorCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.valueobjects.Profile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Debtor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Profile profile;

    public Debtor(String name, String address, String email, String phone) {
        this.profile = new Profile(name, address, email, phone);
    }

    public Debtor() {
        this.profile = new Profile("", "", "", "");
    }

    /**
     * Constructor that is used when is created a Bill and gives only the name.
     */
    public Debtor (String name) {
        this.profile = new Profile(name, "", "", "");
    }

    public Debtor(CreateDebtorCommand command){
        this.profile = new Profile(command.name(), command.address(), command.email(), command.phone());
    }
}