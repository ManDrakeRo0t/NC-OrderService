package ru.bogatov.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.bogatov.orderservice.dto.OrderListDto;
import ru.bogatov.orderservice.entity.Order;
import ru.bogatov.orderservice.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;

    OrderController(@Autowired OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("")
    public OrderListDto getAll(){
        OrderListDto listDto = new OrderListDto();
        listDto.setOrderList(orderService.getAll());
        return listDto;
    }

    @GetMapping("/{id}")
    public Order getOneById(@PathVariable String id){
        return orderService.getOneById(id);
    }
    @PostMapping
    public void addOrder(@RequestBody Order order){
        orderService.addOrder(order);
    }
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable String id){
        orderService.deleteOrder(id);
    }
    @PutMapping("/{id}")
    public void editOrder(@PathVariable String id,@RequestBody Order order){
        orderService.editOrder(id,order);
    }
    @PatchMapping("/{id}")
    public void editStatus(@PathVariable String id,@RequestBody String statusId){
        orderService.editStatus(id,statusId);
    }

}
