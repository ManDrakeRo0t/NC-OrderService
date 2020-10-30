package ru.bogatov.orderservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bogatov.orderservice.entity.Status;

import java.util.Optional;
import java.util.UUID;

public interface StatusRepository extends JpaRepository<Status, UUID> {
    public Optional<Status> findStatusById(UUID id);
}
