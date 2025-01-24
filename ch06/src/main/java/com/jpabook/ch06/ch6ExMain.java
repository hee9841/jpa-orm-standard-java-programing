package com.jpabook.ch06;


import com.jpabook.ch06.domain.Member;
import com.jpabook.ch06.domain.Team;
import com.jpabook.ch06.manager.BaseEntityManager;

public class ch6ExMain {

    public static void main(String[] args) {
        BaseEntityManager bem = new BaseEntityManager();
        
        bem.execute(em -> {
            Member member = new Member("userName");
            em.persist(member);
            
            Team teamA = new Team("teamA");
            teamA.getMembers().add(member); //이렇게 되면 MEMBER 테이블의 TEAM_ID(FK)를 업데이트 침
            em.persist(teamA);
        });

    }
}
