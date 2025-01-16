package com.jpabook;



import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class Ch02Ex {

    public void logic_ex1(EntityManagerFactory emf) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            //저장
            Member member = new Member();
            member.setId(1L);
            member.setName("member1");
            em.persist(member);


            //조회
            Member findMember = em.find(Member.class, 1L);
            System.out.println("member name: " + findMember.getName());

            //update
            findMember.setName("newName");
            Member findMember2 = em.find(Member.class, 1L);
            System.out.println("update member name: " + findMember2.getName());


            //JPQL 전체 맴버 조회?? ->
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                .getResultList();

            for (Member m : result) {
                System.out.println(m.getName());
            }

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

}
