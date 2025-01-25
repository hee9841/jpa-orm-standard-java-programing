package com.jpabook.ch07.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
//@DiscriminatorValue("A")   //Default가 클래스 네임
@Entity
public class Book extends Item {

    private String author;
    private String isbn;

}
