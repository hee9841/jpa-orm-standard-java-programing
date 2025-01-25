package com.jpabook.ch07.domain.identifying;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Entity
public class Child {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "PARENT_ID1",
            referencedColumnName = "PARENT_ID1"),
        @JoinColumn(name = "PARENT_ID2",
            referencedColumnName = "PARENT_ID2")
    })
    private Parent parent;

    public Child(String name, Parent parent) {
        this.name = name;
        this.parent = parent;
    }

    public Child() {
    }
}
