package com.jpabook.ch09.manger;


import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.AccessLevel;
import lombok.Getter;


public class EntityManagerFactoryProvider {

    @Getter(AccessLevel.PROTECTED)
    private static final EntityManagerFactory emf;

    private EntityManagerFactoryProvider() {
    }

    static {
        emf = Persistence.createEntityManagerFactory("ch09");

        Runtime.getRuntime().addShutdownHook(new Thread(EntityManagerFactoryProvider::closeEnf) );
    }

    private static void closeEnf() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("EntityManagerFactory closed.");
        }
    }
}
