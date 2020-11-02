package ru.bogatov.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.bogatov.orderservice.entity.Order;
import ru.bogatov.orderservice.service.OrderService;

import javax.print.URIException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;

    OrderController(@Autowired OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("")
    public List<Order> getAll(){
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    public Order getOneById(@PathVariable String id){
        return orderService.getOneById(id);
    }
    @PostMapping
    public ResponseEntity<Order> addOrder(@RequestBody Order order){
        orderService.addOrder(order);
        return ResponseEntity.status(201).body(new Order());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable String id){
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok("was deleted");
        }catch (RuntimeException e){
            return ResponseEntity.status(500).build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Order> editOrder(@PathVariable String id,@RequestBody Order order){
        try {
            return ResponseEntity.ok(orderService.editOrder(id,order));
        }catch (RuntimeException e){
            return ResponseEntity.status(500).build();
        }

    }
    @PatchMapping("/{id}")
    public ResponseEntity<Order> editStatus(@PathVariable String id, @RequestBody String statusId){
        try {
            return ResponseEntity.ok(orderService.editStatus(id,statusId));
        }catch (RuntimeException e){
            return ResponseEntity.status(500).build();
        }
     }

}
