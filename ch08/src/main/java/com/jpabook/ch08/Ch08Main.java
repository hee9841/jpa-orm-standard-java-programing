package com.jpabook.ch08;


import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Ch08Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ch08");

        emf.close();
    }
}
