package com.jpabook.ch05.manager;

import jakarta.persistence.EntityManager;

@FunctionalInterface
public interface EntityManagerOperation {
    void execute(EntityManager em);
}
