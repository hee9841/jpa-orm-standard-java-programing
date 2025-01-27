package com.jpabook.ch08.manager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class BaseEntityManager {
    public void execute(EntityManagerOperation operation) {
        EntityManager em = EntityManagerFactoryProvider.getEmf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            operation.execute(em);
            System.out.println("[info] : before Commit");
            tx.commit();
            System.out.println("[info] : after Commit");
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
}
