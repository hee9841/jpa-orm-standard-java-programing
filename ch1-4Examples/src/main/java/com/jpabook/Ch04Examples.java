package com.jpabook;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

public class Ch04Examples {

    private final EntityManagerFactory emf;

    public Ch04Examples(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public void strategyIdentity() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        System.out.println("======== BEFORE ==========");
        em.persist(new Member("Member"));
        System.out.println("======== AFTER ==========");
        tx.commit();
        em.close();
    }

    public void strategy_Seq() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        System.out.println("======== BEFORE ==========");
        em.persist(new MemberForSequence("Member"));
        System.out.println("======== AFTER ==========");
        tx.commit();
        em.close();
    }

    public void strategy_Seq_allocation() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        MemberForSequence memberA = new MemberForSequence("MemberA");
        MemberForSequence memberB = new MemberForSequence("MemberB");
        MemberForSequence memberC = new MemberForSequence("MemberC");
        System.out.println("======== BEFORE ==========");

        em.persist(memberA);    //1, 51
        em.persist(memberB);    // MEM
        em.persist(memberC);    // MEM

        System.out.println("memberA.getId() = " + memberA.getId());
        System.out.println("memberB.getId() = " + memberB.getId());
        System.out.println("memberC.getId() = " + memberC.getId());

        System.out.println("======== AFTER ==========");
        tx.commit();
        em.close();

        EntityManager em2 = emf.createEntityManager();
        EntityTransaction tx2 = em2.getTransaction();
        tx2.begin();
        MemberForSequence memberA2 = new MemberForSequence("MemberA2");
        MemberForSequence memberB2 = new MemberForSequence("MemberB2");
        MemberForSequence memberC2 = new MemberForSequence("MemberC2");
        System.out.println("======== BEFORE ==========");

        em2.persist(memberA2);    // MEM
        em2.persist(memberB2);    // MEM
        em2.persist(memberC2);    // MEM

        System.out.println("memberA2.getId() = " + memberA2.getId());
        System.out.println("memberB2.getId() = " + memberB2.getId());
        System.out.println("memberC2.getId() = " + memberC2.getId());

        System.out.println("======== AFTER ==========");
        tx2.commit();
        em2.close();
    }

}

