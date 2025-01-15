package com.jpabook;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //저장
//            logic_save(em);

            //조회 & update
//            Member findMember = em.find(Member.class, 1L);
//            findMember.setName("newName");

            //JPQL 전체 맴버 조회?? ->
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                .getResultList();

            //페이ㅣ지 네이션 setFirtResult, setMasResults..

            //테이블 대상이 아니라, 객체임
            //sql -> 테이블 대상으로 쿼리
            //jpql -> 객체 대상으로 쿼리, 객체지향 sql

            for (Member m : result) {
                System.out.println(m.getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void logic_save(EntityManager em) {
        Member member = new Member();
        member.setId(1L);
        member.setName("member1");
        em.persist(member);
    }
}
