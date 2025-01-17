package com.jpabook;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class Ch3LogicsExample {
    
    private final EntityManagerFactory emf;

    public Ch3LogicsExample(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * 엔티티 생명 주기(영속성에 대한 예제)
     */
    public void entityLifeCycle(long memberId) {

        System.out.println("========= Start entityLifeCycle ==============");
        // 공장에서 엔팉 ㅣ매지너 생성, 비용이 거의 안듬 
        // -> 여러 스레드가 동시에 점근하면 동시성 문제가 발생 -> 공유하면 안됨
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            //비영속 상태
            Member member = new Member();
            member.setId(memberId);
            member.setName("HelloJPA");

            //영속 상태 -> 이때 db에 저장 되는 것이 아님
            System.out.println("==============BEFORE=============");
            em.persist(member);
//            em.detach(member);  //연속성 컨텍스트에서 다시 지우는거
//            em.remove(member);  //영구 저장을 데이터베이스에서 지우겟어..??
            System.out.println("==============AFTER=============");


            tx.commit();    //트랜젝션 커밋하는 시점에 쿼리가 날려짐
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        System.out.println("========= End entityLifeCycle ==============");
    }

    /**
     * 1차 캐시 예제 - 1
     * 영속성 컨텍스트의 1차 캐시에 find하는 객체가 있으면 캐시에서 값을 가져옴
     */
    public void firstCache(long memberId) {
        System.out.println("========= Start firstCache ==============");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            //비영속 상태
            Member member = new Member();
            member.setId(memberId);
            member.setName("HelloJPA");
            em.persist(member);

            //조회를 했는데 select 쿼리를 안날림 -> 1차 캐시에서 가져옴
            Member findMember = em.find(Member.class, memberId);

            System.out.println(findMember.getId());
            System.out.println(findMember.getName());


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        System.out.println("========= End firstCache ==============");
    }

    /**
     * 차 캐시 예제 - 2
     * 1차 캐시에 없는 값은 쿼리를 날림
     * firstCache는 select 없이 값을 가져오는데
     * 해당 함수는 select 쿼리를 날림
     */
    public void notInFistCache(long memberId) {
        addMember(memberId); //member commit
        System.out.println("========= Start not in FistCache ==============");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            Member findMember = em.find(Member.class, memberId);
            System.out.println(findMember.getId());
            System.out.println(findMember.getName());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        System.out.println("========= End not in FistCache ==============");
    }

    /**
     * 영속 엔티티 동일성 보장 예제
     */
    public void entityIdentity(long memberId) {

        addMember(memberId); //add member
        System.out.println("========= Start entityIdentity ==============");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Member a = em.find(Member.class, memberId);
            Member b = em.find(Member.class, memberId);


            System.out.println(a == b);
            //자바 컬랙션 처럼 동일성 보장해줌
            //1차 캐시로 반복가능한 읽기(REPEATABLE READ) 등급의 트랜잭션 격리 수준을 데이터베이스가 아닌
            //애플리케이션 차원에서 제공한다.

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        System.out.println("========= end entityIdentity ==============");
    }


    /**
     * 트랜잭션을 지원하는 쓰기 지연
     */
    public void transactionWriteBehind(long memberAId, long memberBId) {
        System.out.println("========= Start transactionWriteBehind ============");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin(); //트랜잭션 시작

        try {
            Member memberA = new Member(memberAId, "memberA");
            Member memberB = new Member(memberBId, "memberB");

            em.persist(memberA);
            em.persist(memberB);
            //여기서 INSERT SQL를 디비에 보내지 않는다. : 쓰기 지연 SQL 저장소에 쌓이게 됨
            System.out.println("!!!!!!!! After em.persist !!!!!!!!");
            // 쿼리가 두번 나가는데 -> 굳이...??
            // 버퍼링 기능 :
            // 위에처럼 하면, persist마다 쿼리를 날리면 최적화하는 여지가 없음
            // 쌓여있는 쿼리를 한번에 보낼 수 있음 : jdbc 배치

            tx.commit();    //커밋하는 순간 데이터베이스에 INSERT 쿼리를 날린다.
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        System.out.println("========= End transactionWriteBehind ============");
    }

    /**
     * 변경감지
     */
    public void dirtyChecking(long memberId) {
        addMember(memberId); //add member
        System.out.println("========= Start dirtyChecking ============");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin(); //트랜잭션 시작

        try {
            Member findMember = em.find(Member.class, memberId);
            findMember.setName("new Name");

            tx.commit();    //커밋하면 update 쿼리 나감
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        System.out.println("!!!!!! dirtyChecking !!!!!!");

        em = emf.createEntityManager();
        tx = em.getTransaction();

        tx.begin();
        try {
            Member findMember = em.find(Member.class, memberId);
            System.out.println("update name: "  + findMember.getName());

            tx.commit();    //커밋하면 update 쿼리 나감
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        System.out.println("========= End dirtyChecking ============");
    }


    /**
     * 플러시 예제
     */
    public void flushEx(long memberId) {
        System.out.println("========= Start flushEx ============");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            Member member = new Member(memberId, "member");
            em.persist(member);

            em.flush(); //강제로 호출하면...? -> 이 시점에 쿼리가 나감
            System.out.println("!!!!! call flush !!!!!");


            tx.commit();    //그리고 db 트랜젝션이 커밋됨
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        System.out.println("========= End Flush ============");
    }

    /**
     * 준영속 예제
     */
    public void detachedEx(long memberId) {
        addMember(memberId); //add member
        System.out.println("========= Start detachedEx ============");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            //현재 findMember는 영속 상태
            Member findMember = em.find(Member.class, memberId);
            findMember.setName("AAAAAA");

            //더이상 영속성 컨택스트에서 관리하기 싫어...
            em.detach(findMember);  //jpa에서 관리를 안함...

            em.clear();
            Member findMember2 = em.find(Member.class, memberId);   // select 쿼리 두번 나감
            //위에서 clear 해서
            
            tx.commit();    //그래서 커밋할 때 아무것도 안함. select 쿼리만 나가고 update는 안나감
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        System.out.println("========= End detachedEx ============");
    }

    /**
     * em.clear() 예제
     */
    public void clear(long memberId) {
        addMember(memberId);
        System.out.println("========= Start clear ============");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            Member member = em.find(Member.class, memberId);
            em.clear();

            member.setName("new Name");

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }


        findMember(memberId);
    }

    /**
     * em.merge() 예제
     */
    public void merge(long memberId) {
        System.out.println("========= Start merge ============");

        Member member = createMemberForMerge(memberId, "member");
        member.setName("new Name"); //준영속 상태에서 변경
        mergeMember(member);    //준영속 상태의 엔티티의 변경사항을 반영하기 위해 mergeMember에서 다시 영속상태로 변경
    }

    private void mergeMember(Member member) {
        System.out.println("//==영속성 컨텍스트2 시작==//");
        EntityManager em2 = emf.createEntityManager();
        EntityTransaction tx2 = em2.getTransaction();

        tx2.begin();
        Member mergedMember = em2.merge(member);//정확히는 member 엔티티가 준영속상태에서 영속 상태로 변경 되는 것이 아니라,
        // mergedMember라는 새로운 영속 상태의 엔티티가 반영된다.
        member.setName("new new Name"); //member는 이제 사용할 필요 가 없음
        tx2.commit();

        //준영속 상태
        System.out.println("member = " + member.getName());

        //영속상태
        System.out.println("mergedMember = " + mergedMember.getName());



        System.out.println("em2 contains member = " + em2.contains(member));
        System.out.println("em2 contains mergedMember = " + em2.contains(mergedMember));

        em2.close();

        EntityManager em3 = emf.createEntityManager();
        EntityTransaction tx3 = em3.getTransaction();
        tx3.begin();
        Member findMember = em3.find(Member.class, member.getId());
        System.out.println("findMember = " + findMember.getName());
        tx3.commit();

        em3.close();
        System.out.println("//==영속성 컨텍스트2 종료==//");
    }

    private Member createMemberForMerge(long id, String userName) {
        System.out.println("//==영속성 컨텍스트1 시작==//");
        EntityManager em1 = emf.createEntityManager();
        EntityTransaction tx1 = em1.getTransaction();
        tx1.begin();

        Member member = new Member(id, userName);
        em1.persist(member);
        tx1.commit();
        
        em1.close();    //영속성 컨텍스트1 종료, member 엔티티는 준영속 상태

        System.out.println("//==영속성 컨텍스트1 종료==//");

        return member;
    }


    private void addMember(long memberId) {

        System.out.println("========= Start addMember ============");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Member member = new Member(memberId, "member");
            em.persist(member);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    private void findMember(long memberId) {
        System.out.println("========= Start findMember ============");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Member member = em.find(Member.class, memberId);
            System.out.println("member.toString() = " + member.toString());
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }



    //todo detach 관련해서 insert 쿼리가 로그의 찍히는 이유 찾아보기
    public void test2(long memberId) {

        addMember(memberId);

        System.out.println("========= Start test2 ============");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
//            Member member = new Member(memberId, "member");
//            Member member = em.find(Member.class, memberId);
//            member.setName("newName");
//            em.persist(member);

            Member member = em.find(Member.class, memberId);
            member.setName("newName");
            em.flush();
            em.detach(member);
//            Member member = new Member(memberId, "member");
//            em.persist(member);
//            em.detach(member);


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    public void test() {
        System.out.println("========= add member ============");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Member member = new Member(1L, "member");
            Member member2 = new Member(2L, "member2");

            em.persist(member);
            em.persist(member2);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        em = emf.createEntityManager();
        tx = em.getTransaction();

        tx.begin();

        System.out.println("========= detached member ============");
        try {
            Member member1 = em.find(Member.class, 1L);
            Member member2 = em.find(Member.class, 2L);

            //member1 detached, update
            em.detach(member1);
            member1.setName("new Name");

            //member2 detached -> update -> merge
            em.detach(member2);
            em.flush();
            System.out.println("========= fulushed ============");
            member2.setName("new Name");
            em.merge(member2);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        System.out.println("========= select * member ============");

        em = emf.createEntityManager();
        tx = em.getTransaction();

        tx.begin();

        try {
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                .getResultList();

            for (Member member : result) {
                System.out.println("member = " + member.toString());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }


    }
}
