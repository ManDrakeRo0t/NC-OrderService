package ru.bogatov.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bogatov.orderservice.client.UserServiceClient;
import ru.bogatov.orderservice.dao.OrdersRepository;
import ru.bogatov.orderservice.dao.StatusRepository;
import ru.bogatov.orderservice.dto.Customer;
import ru.bogatov.orderservice.entity.Order;
import ru.bogatov.orderservice.entity.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    private OrdersRepository ordersRepository;
    private StatusRepository statusRepository;
    private UserServiceClient userServiceClient;

    OrderService(@Autowired OrdersRepository ordersRepository,
                 @Autowired StatusRepository statusRepository,
                 @Autowired UserServiceClient userServiceClient){
        this.ordersRepository = ordersRepository;
        this.statusRepository = statusRepository;
        this.userServiceClient = userServiceClient;
    }

    public Order getOneById(String id){
        return ordersRepository.getOrderById(UUID.fromString(id));
    }
     public List<Order> getAll(){
        return ordersRepository.findAll();
     }
     public void addOrder(Order order){
        order.setCustomer_id(order.getCustomer_id());
        order.setStatus(findStatus("22bd050f-e9e8-42ac-b938-5e837cc0995e"));
        ordersRepository.save(order);
     }
     public void deleteOrder(String id){

        ordersRepository.deleteById(UUID.fromString(id));
     }

     public void editOrder(String id,Order order){
        Order orderFromBd = findOrder(id);
        orderFromBd.update(order);
        ordersRepository.save(orderFromBd);
     }

     public void editStatus(String orderId,String statusId){
        Order order = findOrder(orderId);
        Status status = findStatus(statusId);
        order.setStatus(status);
        ordersRepository.save(order);
     }

     public Order findOrder(String id){
         Optional<Order> optionalOrderFromDb = ordersRepository.findOrderById(UUID.fromString(id));
         if(!optionalOrderFromDb.isPresent()) throw new RuntimeException("cant find order with id : " + id);
         return optionalOrderFromDb.get();
     }

     public Status findStatus(String id){
         Optional<Status> optionalStatus = statusRepository.findStatusById(UUID.fromString(id));
         if(!optionalStatus.isPresent()) throw new RuntimeException("cant find order status with id : " + id);
         return optionalStatus.get();
     }


}
