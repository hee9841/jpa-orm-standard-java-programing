package com.jpabook.jpashop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DELIVERY_ID")
    private Long id;
    
    private String city;

    private String street;

    private String zipCode;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    //양방향
    @OneToOne(mappedBy = "delivery")
    private Order order;

}
