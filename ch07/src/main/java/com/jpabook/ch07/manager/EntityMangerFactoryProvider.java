package com.jpabook.ch07.manager;


import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Getter;

public class EntityMangerFactoryProvider {

    @Getter
    private static final EntityManagerFactory emf;

    private EntityMangerFactoryProvider() {
    }

    static {
        emf = Persistence.createEntityManagerFactory("ch07");

        Runtime.getRuntime().addShutdownHook(new Thread(EntityMangerFactoryProvider::closeEnf));
    }

    public static void closeEnf() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("EntityManagerFactory closed.");
        }
    }
}
