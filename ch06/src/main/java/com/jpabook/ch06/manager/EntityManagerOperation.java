package com.jpabook.ch06.manager;

import jakarta.persistence.EntityManager;

@FunctionalInterface
public interface EntityManagerOperation {
    void execute(EntityManager em);
}
