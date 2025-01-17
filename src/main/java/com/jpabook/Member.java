package com.jpabook;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "MEMBER")
//@org.hibernate.annotations.DynamicUpdate
class Member {

    @Id
    private Long id;

    @Column(name = "NAME")
    private String userName;
    private Integer age;

    public Member(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public Member() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "id:" + id + ", " + "name: " + userName;
    }
}
