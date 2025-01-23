package com.jpabook.ch05;


import com.jpabook.ch05.manager.EntityMangerFactoryProvider;
import com.jpabook.ch05.domain.Ch05Repository;
import com.jpabook.ch05.domain.Member;
import java.util.List;

public class Ch5ExMain {

    public static void main(String[] args) {

        Ch05Repository repo = new Ch05Repository();
        repo.ex1();
        List<Member> members = repo.saveMember();
        Member member1 = members.get(0);
        repo.findMemberBy(member1.getId());
        repo.useJPQLFindAllBy(member1.getTeam().getName());
        repo.updateRelation(member1.getId(), "team2");
        repo.deleteRelation(member1.getId());
        System.out.println(member1.getTeam().getId());
        repo.biDirectionFindBy(member1.getTeam().getId());
        repo.saveNonOwner();

        EntityMangerFactoryProvider.closeEnf();
    }

}
