package com.jpabook.ch08.manager;


import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Getter;

public class EntityManagerFactoryProvider {
    @Getter
    private static final EntityManagerFactory emf;

    private EntityManagerFactoryProvider() {
    }

    static {
        emf = Persistence.createEntityManagerFactory("08");

        Runtime.getRuntime().addShutdownHook(new Thread(EntityManagerFactoryProvider::closeEnf) );
    }

    private static void closeEnf() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("EntityManagerFactory closed.");
        }
    }
}
