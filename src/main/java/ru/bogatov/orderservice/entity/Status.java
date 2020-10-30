package ru.bogatov.orderservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "status")
@Getter
@Setter
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    @OneToMany( fetch = FetchType.EAGER)
    private List<Order> orders;
}
