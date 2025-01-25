package com.jpabook.ch07.manager;

import jakarta.persistence.EntityManager;

@FunctionalInterface
public interface EntityManagerOperation {
    void execute(EntityManager em);
}
