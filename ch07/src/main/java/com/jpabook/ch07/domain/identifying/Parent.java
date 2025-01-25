package com.jpabook.ch07.domain.identifying;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
//@IdClass(ParentId.class) //IdClass 사용 시
public class Parent {

    /**
     * IdClass 사용 시
     *
     * @Id
     * @Column(name = "PARENT_ID1")
     * private Long id1;
     * @Id
     * @Column(name = "PARENT_ID2")
     * private Long id2;
     **/

    @Setter
    @EmbeddedId
    private ParentId id;

    private String name;

    public Parent(String name) {
        this.name = name;
    }

    public Parent() {
    }
}
