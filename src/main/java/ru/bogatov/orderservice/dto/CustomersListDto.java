package ru.bogatov.orderservice.dto;

import java.util.List;

public class CustomersListDto {
    List<Customer> customers;

    public List<Customer> getCustomers(){
        return customers;
    }
}
