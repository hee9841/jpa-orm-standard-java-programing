package com.jpabook.ch07.domain;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Inheritance(strategy = InheritanceType.JOINED) //join 전략
@DiscriminatorColumn(name = "DTYPE")    //DTYPE(디폴트) name으로 디폴트값으로..

//단일 테이블 전략(Dtype
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)   //성능상 이점이 있음
//@DiscriminatorColumn    //해당 어노테이션 없어도 DTYPE 들어감

//db 전략만 바꿨는데 코어 코드를 바꾸지 않아도.... 전략만 바꾸면 됨

// 구현 클래스마다 테이블 전략
//아이템 테이블 없애벌리고, name, price 같은 속성들은 중복되게 허용
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
// 이 전략이 부모타입으로 조회하면....? unionALL로 다 검색하는....


//abstract 클래스로 만들어야됨(처음부터)
@ToString
@Getter
@Setter
@Entity
public abstract class Item extends BaseEntity {
    @Id @GeneratedValue
    private Long id;

    private String name;

    private int price;
}

//extends만 하면 한테이블에 다 때려 박음
// Inheritance의 strategy의 기본값이 SINGLE_TABLE
//Hibernate:
//    create table Item (
//        price integer not null,
//        id bigint not null,
//        DTYPE varchar(31) not null,
//        actor varchar(255),
//        artist varchar(255),
//        author varchar(255),
//        director varchar(255),
//        isbn varchar(255),
//        name varchar(255),
//        primary key (id)
//    )
// ---
// joined 로 하면 pk가 fk로
