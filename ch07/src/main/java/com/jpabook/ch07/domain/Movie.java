package com.jpabook.ch07.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Movie extends Item {

    private String director;
    private String actor;
}
