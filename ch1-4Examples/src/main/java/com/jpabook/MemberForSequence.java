package com.jpabook;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "MEMBER_FOR_SEQUENCE")
@SequenceGenerator(
    name = "MEMBER_SEQ_GENERATOR",
    sequenceName = "MEMBER_SEQ",
    initialValue = 1, allocationSize = 50
)
class MemberForSequence {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
        generator = "MEMBER_SEQ_GENERATOR")
    private Long id;

    private String userName;


    public MemberForSequence(String userName) {
        this.userName = userName;
    }

    public MemberForSequence() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    @Override
    public String toString() {
        return "id:" + id + ", " + "name: " + userName;
    }
}
