package ru.bogatov.orderservice.dto;

import lombok.Data;

import javax.management.relation.Role;
import java.util.Set;
import java.util.UUID;

@Data
public class Customer {
    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phone;
    private Addresses addresses;
    private PaidType paidType;
    private Set<Role> roles;
}
