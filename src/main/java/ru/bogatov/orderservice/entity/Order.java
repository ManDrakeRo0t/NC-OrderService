package ru.bogatov.orderservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "my_orders") //todo RestTemplate cleint and roles
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private Date deliveryDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private Status status;
    private UUID customer_id;
    private Boolean paid;

    public void update(Order order){
        if(order.name != null) this.name = order.name;
        if(order.deliveryDate != null) this.deliveryDate = order.deliveryDate;
        if(order.paid != null) this.paid = order.paid;
    }

}