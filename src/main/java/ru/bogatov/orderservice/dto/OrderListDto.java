package ru.bogatov.orderservice.dto;

import lombok.Data;
import ru.bogatov.orderservice.entity.Order;

import java.util.List;

@Data
public class OrderListDto {
    List<Order> orderList;
}
