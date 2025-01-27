package com.jpabook.ch08.Exampels;

import com.jpabook.ch08.domain.Member;
import com.jpabook.ch08.domain.Order;
import com.jpabook.ch08.domain.Product;
import com.jpabook.ch08.domain.Team;
import com.jpabook.ch08.manager.BaseEntityManager;
import java.util.List;

public class LazyLoadEx {

    private final static BaseEntityManager bem = new BaseEntityManager();

    public void lazyLoadEx() {
        bem.execute(em -> {
            Team teamA = new Team("teamA");
            em.persist(teamA);

            Member member1 = new Member("member1", teamA);
            em.persist(member1);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member1.getId());
            // member 만 조회
            //    select
            //        m1_0.id,
            //        m1_0.name,
            //        m1_0.TEAM_ID
            //    from
            //        Member m1_0
            //    where
            //        m1_0.id=?

            System.out.println("팀조회 : " + findMember.getTeam().getClass());
            // 팀조회 : class com.jpabook.ch08.domain.Team$HibernateProxy$uwr2HGFd
            // 프록시로 가져옴

            // 실제 team을 사용하는 시점에 초기화
            System.out.println(
                "findMember.getTeam().getName() = " + findMember.getTeam().getName());
            // 이시점에 쿼리 나감
            //    select
            //        t1_0.id,
            //        t1_0.name
            //    from
            //        Team t1_0
            //    where
            //        t1_0.id=?

        });
    }

    public void FetachType을_EAGER로_설정_시() {
        bem.execute(em -> {
            Team teamA = new Team("teamA");
            em.persist(teamA);
            Team TeamB = new Team("TeamB");
            em.persist(TeamB);

            Member member1 = new Member("member1", teamA);
            em.persist(member1);

            Member member2 = new Member("member2", TeamB);
            em.persist(member2);

            System.out.println("!!!!!!!!!!!!!!!!!!!!");

            em.flush();
            em.clear();

            //FetchType.EAGER 설정 시 JPQL N+1 문제
            List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();
            //쿼리가 2번 나감
            //jpql은 sql 그대로 번역 됨 -> select * from MEMBER -> 인데 팀이 즉서로딩이네...??
            //그래서 팀을 select 하는 쿼리가 나감
            //근데 전체 팀이 10명, ...1000명 이면...??
            //그만큼의 select 쿼리가 나감....
            //Hibernate:
            //    /* select
            //        m
            //    from
            //        Member m */ select
            //            m1_0.id,
            //            m1_0.name,
            //            m1_0.TEAM_ID
            //        from
            //            Member m1_0
            //Hibernate: teamA
            //    select
            //        t1_0.id,
            //        t1_0.name
            //    from
            //        Team t1_0
            //    where
            //        t1_0.id=?
            //Hibernate: 이게 -> teamB
            //    select
            //        t1_0.id,
            //        t1_0.name
            //    from
            //        Team t1_0
            //    where
            //        t1_0.id=?

        });
    }

    public void jpqlFetchJoin() {
        bem.execute(em -> {
            Team teamA = new Team("teamA");
            em.persist(teamA);
            Team TeamB = new Team("TeamB");
            em.persist(TeamB);

            Member member1 = new Member("member1", teamA);
            em.persist(member1);

            Member member2 = new Member("member2", TeamB);
            em.persist(member2);

            System.out.println("!!!!!!!!!!!!!!!!!!!!");

            em.flush();
            em.clear();

            //FetchType.LAZY 설정 후 페치 조인으로 가져옴
            // member team 같이 조회하겠다.
            List<Member> members = em.createQuery("select m from Member m join fetch m.team",
                    Member.class)
                .getResultList();

            //Hibernate: member랑 팀을 같이 조인
            //    /* select
            //        m
            //    from
            //        Member m
            //    join
            //
            //    fetch
            //        m.team */ select
            //            m1_0.id,
            //            m1_0.name,
            //            t1_0.id,
            //            t1_0.name
            //        from
            //            Member m1_0
            //        join
            //            Team t1_0
            //                on t1_0.id=m1_0.TEAM_ID

        });
    }


    public void proxy_collection_wrapper() {
        bem.execute(em -> {
            Team teamA = new Team("teamA");
            em.persist(teamA);

            Product productA = new Product("productA");
            em.persist(productA);

            Member member1 = new Member("member1", teamA);
            em.persist(member1);

            Order order1 = new Order("order1");
            order1.addProduct(productA);
            member1.addOrder(order1);

            Order order2 = new Order("order2");
            order2.addProduct(productA);
            member1.addOrder(order2);

            em.persist(order1);
            em.persist(order2);

            em.flush();
            em.clear();

            System.out.println("!!!!!!!!!!!!");
            //조회
            Member findMember = em.find(Member.class, member1.getId());
            System.out.println("pre findMember.getOrders()");
            List<Order> orders = findMember.getOrders();
            System.out.println("orders.getClass().getName() = " + orders.getClass());
            // 결과 : org.hibernate.collection.spi.PersistentBag

            System.out.println("orders.get(0) = " + orders.get(0));
            //그제서야 가져옴
            //Hibernate:
            //    select
            //        o1_0.MEMBER_ID,
            //        o1_0.id,
            //        o1_0.name,
            //        p1_0.id,
            //        p1_0.name
            //    from
            //        ORDERS o1_0
            //    left join
            //        Product p1_0
            //            on p1_0.id=o1_0.PRODUCT_ID
            //    where
            //        o1_0.MEMBER_ID=?
            //orders.get(0) = Order{product=Product(id=1, name=productA), name='order1', id=1}
        });
    }


}
