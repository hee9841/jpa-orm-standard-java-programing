package com.jpabook.ch07.domain.jointable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class OneToOneJoinChild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    //양방향시
    @OneToOne(mappedBy = "child")
    private OneToOneJoinParent parent;
}
