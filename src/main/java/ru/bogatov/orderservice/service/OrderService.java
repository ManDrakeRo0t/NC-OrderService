package ru.bogatov.orderservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bogatov.orderservice.client.UserServiceClient;
import ru.bogatov.orderservice.dao.OrdersRepository;
import ru.bogatov.orderservice.entity.Order;
import ru.bogatov.orderservice.entity.Status;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    private OrdersRepository ordersRepository;
    private UserServiceClient userServiceClient;

    OrderService(@Autowired OrdersRepository ordersRepository,
                 @Autowired UserServiceClient userServiceClient){
        this.ordersRepository = ordersRepository;
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
        order.setStatus(Status.NEW);
        ordersRepository.save(order);
     }
     public void deleteOrder(String id) throws RuntimeException{
        Order order = findOrder(id);
        ordersRepository.delete(order);
     }

     public Order editOrder(String id, Order order) throws RuntimeException{
        Order orderFromBd = findOrder(id);
        orderFromBd.update(order);
        return ordersRepository.save(orderFromBd);
     }

     public Order editStatus(String orderId,String status) throws RuntimeException{
        Order order = findOrder(orderId);
        order.setStatus(Status.valueOf(status));
        return ordersRepository.save(order);
     }

     public Order findOrder(String id){
         Optional<Order> optionalOrderFromDb = ordersRepository.findOrderById(UUID.fromString(id));
         if(!optionalOrderFromDb.isPresent()) throw new RuntimeException("cant find order with id : " + id);
         return optionalOrderFromDb.get();
     }

}
