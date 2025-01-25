package com.jpabook.ch07.domain.jointable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class OneToManyJoinChild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHILD_ID")
    private Long id;

    private String name;

    //다대일 일경우
//    @ManyToOne(optional = false)
//    @JoinTable(name = "MANY_ONE_TO_PARETN_CHILD",
//        joinColumns = @JoinColumn(name = "CHILD_ID"),
//        inverseJoinColumns = @JoinColumn(name = "PARENT_ID")
//    )
//    private OneToManyJoinParent parent;

}
