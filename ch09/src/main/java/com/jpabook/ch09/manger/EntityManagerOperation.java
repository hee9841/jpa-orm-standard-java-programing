package com.jpabook.ch09.manger;

import jakarta.persistence.EntityManager;

@FunctionalInterface
public interface EntityManagerOperation {
    void execute(EntityManager em);
}
