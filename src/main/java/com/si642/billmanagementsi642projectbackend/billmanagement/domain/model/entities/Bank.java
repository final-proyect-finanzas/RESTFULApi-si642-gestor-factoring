package com.si642.billmanagementsi642projectbackend.billmanagement.domain.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Bank {
    @Id
    private Long id;

    private String name;
    private Double tax;



}
