package com.jpabook.ch06.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/**
 * N : M -> 1:N, N :1 로 풀어쓰기
 */
@Entity
@Table(name = "ORDERS")
//@IdClass(OrderId.class)   //복합키 사용시
public class Order {

    @Id @GeneratedValue //대리키
    private Long id;

//    @Id //복합키 사용시
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

//    @Id //복합키 사용시
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    private int orderAmount;

    private LocalDateTime orderDate;

}
