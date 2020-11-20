package ru.bogatov.orderservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.bogatov.orderservice.client.AuthenticationServiceClient;
import ru.bogatov.orderservice.client.CustomerServiceClient;
import ru.bogatov.orderservice.client.OfferServiceClient;
import ru.bogatov.orderservice.configuration.JwtProvider;
import ru.bogatov.orderservice.dao.OrdersRepository;
import ru.bogatov.orderservice.dto.Offer;
import ru.bogatov.orderservice.dto.PaidType;
import ru.bogatov.orderservice.entity.Order;
import ru.bogatov.orderservice.entity.Status;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private OrdersRepository ordersRepository;
    private AuthenticationServiceClient authenticationServiceClient;
    private CustomerServiceClient customerServiceClient;
    private OfferServiceClient offerServiceClient;
    private JwtProvider jwtProvider;

    OrderService(@Autowired OrdersRepository ordersRepository,
                 @Autowired AuthenticationServiceClient authenticationServiceClient,
                 @Autowired CustomerServiceClient customerServiceClient,
                 @Autowired OfferServiceClient offerServiceClient,
                 @Autowired JwtProvider jwtProvider){
        this.jwtProvider = jwtProvider;
        this.ordersRepository = ordersRepository;
        this.authenticationServiceClient = authenticationServiceClient;
        this.customerServiceClient = customerServiceClient;
        this.offerServiceClient = offerServiceClient;
    }

    public Order getOneById(String id){
        return findOrder(id);
    }


    public List<Order> getAll(){
        return ordersRepository.findAll();
     }

     public Order addOrder(Order order){
        order.setCustomer_id(order.getCustomer_id());
        order.setStatus(Status.NEW);
        return ordersRepository.save(order);
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

     public ResponseEntity<Offer> getOfferInfo(String id){
        ResponseEntity<Offer> response = offerServiceClient.getOffer(jwtProvider.generateM2mToken(),id);
        if(response.getStatusCode() != HttpStatus.INTERNAL_SERVER_ERROR){
            Offer offer = response.getBody();
            offer.setId(null);
            offer.setPaidTypeId(null);
            return ResponseEntity.status(200).body(offer);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
     }

    public ResponseEntity<Object> createOrder(String token, Offer offer) {
        UUID customerId = authenticationServiceClient.getIdFromToken(token).getBody();
        List<PaidType> customersPaidTypes = customerServiceClient.getCustomersPaidTypes(jwtProvider.generateM2mToken(),customerId.toString());
        List<UUID> paidTypesId = customersPaidTypes.stream().map(PaidType::getId).collect(Collectors.toList());
        if(paidTypesId.contains(offer.getPaidTypeId())){
            Order order = new Order();
            order.setCustomer_id(customerId);
            order.setDeliveryDate(Date.from(LocalDate.now().plusDays(5).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            order.setName("order for " + offer.getCategory());
            order.setOffer_id(offer.getId());
            order.setPaid(false);
            addOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
