package com.jpabook.ch08.manager;

import jakarta.persistence.EntityManager;

@FunctionalInterface
public interface EntityManagerOperation {
    void execute(EntityManager em);
}
