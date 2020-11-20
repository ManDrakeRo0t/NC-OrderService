package ru.bogatov.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.bogatov.orderservice.dto.Offer;
import ru.bogatov.orderservice.entity.Order;
import ru.bogatov.orderservice.service.OrderService;

import javax.print.URIException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/orders")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class OrderController {
    private OrderService orderService;

    OrderController(@Autowired OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("")
    public List<Order> getAll()  {
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOneById(@PathVariable String id){
        try{
            return ResponseEntity.ok().body(orderService.getOneById(id));
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/create")
    public ResponseEntity<Object> createOrder(@RequestHeader(value = "Authorization") String token,
                                              @RequestBody Offer offer){
        return orderService.createOrder(token,offer);
    }
    @GetMapping("/offer-info/{id}")
    public ResponseEntity<Offer> getOfferInfo(@PathVariable String id){
        return orderService.getOfferInfo(id);
    }
    @PostMapping
    public Order addOrder(@RequestBody Order order){
        return orderService.addOrder(order);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable String id){
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok("was deleted");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Order> editOrder(@PathVariable String id,@RequestBody Order order){
        try {
            return ResponseEntity.ok(orderService.editOrder(id,order));
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @PatchMapping("/{id}")
    public ResponseEntity<Order> editStatus(@PathVariable String id, @RequestBody String statusId){
        try {
            return ResponseEntity.ok(orderService.editStatus(id,statusId));
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    } //todo swagger

}
