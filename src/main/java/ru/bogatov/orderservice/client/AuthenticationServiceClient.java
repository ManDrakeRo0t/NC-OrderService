package ru.bogatov.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;
@Component
@FeignClient(name = "authentication-service",url = "http://localhost:8091/auth")
public interface AuthenticationServiceClient {
    @PostMapping("")
    public ResponseEntity<UUID> getIdFromToken(@RequestBody String token);
}
