package ru.bogatov.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.bogatov.orderservice.dto.Offer;

@Component
@FeignClient(name ="offer-service",url = "http://localhost:8093/offers")
public interface OfferServiceClient {
    @GetMapping("/{id}")
    public ResponseEntity<Offer> getOffer(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
                                          @PathVariable String id);
}
