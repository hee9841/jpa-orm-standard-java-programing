package com.jpabook.ch08.Exampels;

import com.jpabook.ch08.domain.Member;
import com.jpabook.ch08.domain.Team;
import com.jpabook.ch08.manager.BaseEntityManager;
import com.jpabook.ch08.manager.EntityManagerFactoryProvider;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Hibernate;

public class ProxyEx {

    private final static BaseEntityManager bem = new BaseEntityManager();

    public void referenceEx() {
        bem.execute(em -> {
            Team teamA = new Team("teamA");
            em.persist(teamA);

            Member member1 = new Member("member1", teamA);
            em.persist(member1);

            em.flush();
            em.clear();

            System.out.println("Flush!!!!!!");

            //getReference 호출하는 시점에는 쿼리를 실행 안함
            // findMember를 실제 사용되는 시점 (findMember.getName())에 쿼리를 호출
            Member findMember = em.getReference(Member.class, member1.getId());
            //class com.jpabook.ch08.domain.Member$HibernateProxy$531IYe3E
            //가짜 엔티티 객체(껍데기는 같은데 안이 텅텅 빈거, id값만 들고 있는) 실제 엔티티를 상속받아서 만듬
            // 프록시 객체는 실제 객체의 참조(target)를 보관
            // 프록시 객체를 호출하면 프록시 객체는 실제 객체 메소드를 호출함
            System.out.println("findMember.getClass() = " + findMember.getClass());
            System.out.println(findMember.getName());   // 이시점에 db에 쿼리를 날림

//            System.out.println("!!!!!!!!!!!!!!!");
//            em.flush();
//            em.clear();
//
//            Member reference = em.getReference(Member.class, member1.getId());
//            System.out.println(reference.getName());

        });
    }

    public void referenceExInsctanceOf() {
        bem.execute(em -> {
            Team teamA = new Team("teamA");
            em.persist(teamA);

            Member member1 = new Member("member1", teamA);
            em.persist(member1);
            Member member2 = new Member("member2", teamA);
            em.persist(member2);

            em.flush();
            em.clear();

            Member findM1 = em.find(Member.class, member1.getId());
            Member findM2 = em.find(Member.class, member2.getId());

            System.out.println("findM1.getClass() == findM2.getClass() = " + (findM1.getClass()
                == findM2.getClass())); //true

            em.flush();
            em.clear();

            Member referenceM1 = em.getReference(Member.class, member1.getId());

            System.out.println(
                "referenceM1.getClass() == findM1.getClass() = " + (referenceM1.getClass()
                    == findM1.getClass())); //false

            //타입 비교는 instance of
            System.out.println(
                "(referenceM1 instanceof Member) = " + (referenceM1 instanceof Member));    // true


        });
    }

    public void 영속성컨텍스트에_엔티티가존재하면_reference_호출시_실제_엔티티_반환() {
        bem.execute(em -> {
            //영속성 컨텍스트에 찾는 엔팉가 있으면 em.getReference()를 호출해도 실제 엔티티를 반환함
            //jpa에서는 같은 영속성 컨텍스트 안에서 조회하면 항상 같다고 나와야함
            //1. 이미 1차 캐시가 있는데
            //2. 중요. jpa에서는 a == a -> 항상 true가 되어야함
            Team teamA = new Team("teamA");
            em.persist(teamA);

            Member member1 = new Member("member1", teamA);
            em.persist(member1);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member1.getId());
            System.out.println("findMember.getClass() = " + findMember.getClass());
            //class com.jpabook.ch08.domain.Member

            Member referenceMember = em.getReference(Member.class, member1.getId());
            System.out.println("referenceMember.getClass() = " + referenceMember.getClass());
            //class com.jpabook.ch08.domain.Member
        });
    }

    public void reference_다음_find_호출하면_find호출한객체는_reference의_프록시객체랑_같다() {
        bem.execute(em -> {
            Team teamA = new Team("teamA");
            em.persist(teamA);

            Member member1 = new Member("member1", teamA);
            em.persist(member1);

            em.flush();
            em.clear();

            //레퍼런스로 가져옴 -> find로 가져옴 -> find는 엔티티 객체? 아니면 프록시 객체일까?
            Member reference = em.getReference(Member.class, member1.getId());
            System.out.println("reference.getClass() = " + reference.getClass());
            //class com.jpabook.ch08.domain.Member$HibernateProxy$pRBROJwX

            Member find = em.find(Member.class, member1.getId());
            System.out.println("find.getClass() = " + find.getClass());
            //class com.jpabook.ch08.domain.Member$HibernateProxy$pRBROJwX

            // 같은 프록시 객체(a == a : true) 되어야하니까
        });
    }

    public void context_초기화시_예외_발생() {
        bem.execute(em -> {
            Team teamA = new Team("teamA");
            em.persist(teamA);

            //영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화하면 문제 발생
            //하이버네이트는 org.hibernate.LazyInitializationException 예외 발생
            Member member1 = new Member("member1", teamA);
            em.persist(member1);

            em.flush();
            em.clear();

            Member reference = em.getReference(Member.class, member1.getId());
            System.out.println("reference.getClass() = " + reference.getClass()); // 프록시

            em.detach(reference);
//            em.clear();   //clear도 에러
            reference.getName();    //org.hibernate.LazyInitializationException 예외 발생
            // Could not initialize proxy ....  no session
        });
    }

    public void 프록시_인스턴스_초기화_여부_확인() {
        bem.execute(em -> {
            Team teamA = new Team("teamA");
            em.persist(teamA);

            //영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화하면 문제 발생
            //하이버네이트는 org.hibernate.LazyInitializationException 예외 발생
            Member member1 = new Member("member1", teamA);
            em.persist(member1);

            em.flush();
            em.clear();

            Member reference = em.getReference(Member.class, member1.getId());
            System.out.println("reference.getClass() = " + reference.getClass()); // 프록시

            EntityManagerFactory emf = EntityManagerFactoryProvider.getEmf();
            System.out.println(
                "pre clear isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(reference)); //false

            System.out.println("(초기화)reference.getName() = " + reference.getName());
            //프록시 인스턴스 초기와 여부 확인
            System.out.println(
                "isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(reference));   //true
        });
    }

    public void 프록시_강제_초기화() {
        bem.execute(em -> {
            Team teamA = new Team("teamA");
            em.persist(teamA);

            //영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화하면 문제 발생
            //하이버네이트는 org.hibernate.LazyInitializationException 예외 발생
            Member member1 = new Member("member1", teamA);
            em.persist(member1);

            em.flush();
            em.clear();

            Member reference = em.getReference(Member.class, member1.getId());
            System.out.println("reference.getClass() = " + reference.getClass()); // 프록시
//            reference.getName(); // 강제 초기화
            Hibernate.initialize(reference);    //강제 초기화
        });
    }

    public void 식별자_호출시_프록시_초기화_여부() {
        bem.execute(em -> {
            Team teamA = new Team("teamA");
//            teamA.setId(1L);
            em.persist(teamA);

            em.flush();
            em.clear();

            Team reference = em.getReference(Team.class, teamA.getId());
            System.out.println(reference.getId());

            EntityManagerFactory emf = EntityManagerFactoryProvider.getEmf();
            System.out.println(
                "isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(reference));
        });
    }
}
