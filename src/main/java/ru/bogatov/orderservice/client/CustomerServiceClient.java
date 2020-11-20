package ru.bogatov.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.bogatov.orderservice.dto.PaidType;

import java.util.List;

@FeignClient(name ="customer-service",url="http://localhost:8091/customers")
@Component
public interface CustomerServiceClient {
    @GetMapping("/{id}/paid-types")
    public List<PaidType> getCustomersPaidTypes(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, @PathVariable String id);
}
