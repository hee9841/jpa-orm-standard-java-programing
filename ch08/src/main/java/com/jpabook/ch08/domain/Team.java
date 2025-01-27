package com.jpabook.ch08.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
//@Access(AccessType.PROPERTY)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @Getter
    private String name;

    public Team(String name) {
        this.name = name;
    }

    public Team() {
    }

//    @Id
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
}
