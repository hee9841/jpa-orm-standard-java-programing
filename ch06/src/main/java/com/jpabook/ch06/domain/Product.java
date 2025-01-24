package com.jpabook.ch06.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id @GeneratedValue
    @Column(name = "PRODUCT_ID")
    private Long id;

    private String name;

    //N:M 양방향
//    @ManyToMany(mappedBy = "products")
//    private List<Member> members = new ArrayList<>();
}
