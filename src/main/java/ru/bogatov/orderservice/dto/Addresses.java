package ru.bogatov.orderservice.dto;

import lombok.Data;

import java.util.UUID;
@Data
public class Addresses {
    private UUID id;
    private String city;
    private String state;
    private String country;
}
