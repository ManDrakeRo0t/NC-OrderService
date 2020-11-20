package ru.bogatov.orderservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class Offer {
    private UUID id;
    private float price;
    private Category category;
    private UUID paidTypeId;
}
