package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities;

import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.aggregates.Wallet;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.commands.CreateCompanyCommand;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.valueobjects.Profile;
import com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.valueobjects.UserId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Profile profile;

    @Embedded
    private UserId userId;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wallet> wallets;

    public Company(CreateCompanyCommand command, UserId userId) {
        this.profile = new Profile(command.name(), command.address(), command.email(), command.phone());
        this.userId = userId;
    }

    public Company() {
        this.profile = new Profile("", "", "", "");
    }
}