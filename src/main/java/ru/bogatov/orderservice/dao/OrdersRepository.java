package ru.bogatov.orderservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bogatov.orderservice.entity.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrdersRepository extends JpaRepository<Order, UUID> {
    public Order getOrderById(UUID id);

    public Optional<Order> findOrderById(UUID id);
}

