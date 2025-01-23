package com.jpabook.ch05.domain;


import com.jpabook.ch05.manager.BaseEntityManager;
import java.util.List;

public class Ch05Repository {

    private static final BaseEntityManager bem = new BaseEntityManager();

    /**
     * 순수 객체 참조 관련 예제
     */
    public void ex1() {
        Team team1 = Team.builder()
            .name("team1")
            .build();

        Member member1 = Member
            .builder()
            .userName("member1")
            .team(team1)
            .build();

        Member member2 = Member
            .builder()
            .userName("member2")
            .team(team1)
            .build();

        Team findTeam = member1.getTeam();
    }


    public List<Member> saveMember() {

        //bidirectional, unidirectional 동일 로직
        System.out.println("===========start saveMember==========");
        //팀1
        Team team1 = new Team("team1");

        //회원1
        Member member1 = new Member("member1");
        member1.setTeam(team1);     //회원 -> 팀참조

        //회원2
        Member member2 = new Member("member2");
        member2.setTeam(team1);

        bem.execute(em -> {
            em.persist(team1);
            em.persist(member1);        //회원 저장
            em.persist(member2);
        });

        return List.of(member1, member2);
    }


    public void findMemberBy(long memberId) {
        System.out.println("===========start findMemberBy==========");
        bem.execute(em -> {
            Member member = em.find(Member.class, memberId);
            System.out.println("객체 그래프 탐색 전");

            Team team = member.getTeam();
            System.out.println("team.getName() = " + team.getName());
        });
    }

    public void useJPQLFindAllBy(String teamName) {
        System.out.println("===========start useJPQLFindAllBy==========");
        String jpql = """
            select m from Member m
            join m.team t
            where t.name=:teamName
            """;

        //트렌젝션 및 엔티티 관리
        bem.execute(em ->{
            em.createQuery(jpql, Member.class)
                .setParameter("teamName", teamName)
                .getResultStream()
                .forEach(System.out::println);
        });
    }

    public void updateRelation(long memberId, String newTeamName) {
        System.out.println("===========start updateRelation==========");
        bem.execute(em -> {
            //새로운 팀
            Team newTeam = new Team(newTeamName);
            em.persist(newTeam);

            //memberId인 회원에 새로운 팀2 설정
            Member member = em.find(Member.class, memberId);
            member.setTeam(newTeam);
        });
    }

    public void deleteRelation(long memberId) {
        System.out.println("===========start deleteRelation==========");

        bem.execute(em -> {
            Member member = em.find(Member.class, memberId);
            member.setTeam(null);   //연관관계시 제거 -> TEAM_ID = null
        });
    }

    public void biDirectionFindBy(long teamId) {
        System.out.println("===========start biDirection==========");
        bem.execute(em -> {
            Team team = em.find(Team.class, teamId);

            System.out.println("[Info] pre get Members");
            team.getMembers()
                .forEach(System.out::println);
            

        });
    }

    public void biDirection_None_Save() {
        System.out.println("===========start biDirection_None_Save ==========");
        //팀1, 회원1,2 저장
        List<Member> members = saveMember();
        Team team = members.get(0).getTeam();
        
        bem.execute(em -> {
            Team team1 = em.find(Team.class, team.getId());
            
            team1.getMembers().add(new Member("new Member")); //무시
            team1.getMembers().add(new Member("new Member2"));
        });
    }

    public void saveNonOwner() {
        System.out.println("===========start saveNonOwner ==========");
        bem.execute(em -> {
            Member member1 = new Member("member1_nonOwner");
            em.persist(member1);

            Member member2 = new Member("member2_nonOwner");
            em.persist(member2);

            Team team = new Team("team_nonOwner");
            
            //주인이 아닌 곳만 연관관계 설정
            team.getMembers().add(member1);
            team.getMembers().add(member2);

            em.persist(team);
        });

        //저장후 데이터 보면
        //team(1L, 'team_nonOwner')
        //member[{name:'member1_nonOwner', team:null},
        // {name:'member2_nonOwner', team:null}]
        // -> 외래키가 저장 안됨
    }

    public void 순수한객체_양방향_설정_주인만() {
        Team team1 = new Team("team1");
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        //연관관계 설정 member -> team
        member1.setTeam(team1);
        member2.setTeam(team1);

        List<Member> members = team1.getMembers();
        System.out.println("members.size() = " + members.size());

        //실행 결과 members.size() = 0
    }

    public void 순수한객체_양방향() {
        Team team1 = new Team("team1");
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        //연관관계 설정 member -> team
        member1.setTeam(team1);
        member2.setTeam(team1);

        //team -> member
        team1.getMembers().add(member1);
        team1.getMembers().add(member2);

        List<Member> members = team1.getMembers();
        System.out.println("members.size() = " + members.size());

        //실행 결과 members.size() = 2
    }

    public void testORM_양방향() {
        System.out.println("===========start testORM_양방향 ==========");

        System.out.println("=========== 편의 메서드 적용 전 ==========");
        bem.execute(em -> {
            Team team1 = new Team("team1");
            em.persist(team1);


            Member member1 = new Member("member1");
            //양방향 관계 설정
            member1.setTeam(team1);     //연관관계 주인, 이 값으로 외래키 관리
            team1.getMembers().add(member1);    //연관관계 주인X, 따라서 저장시에는 사용되지 않음
            em.persist(member1);


            Member member2 = new Member("member2");
            //양팡방 관계 설정
            member2.setTeam(team1);
            team1.getMembers().add(member2);
            em.persist(member2);

        });

        System.out.println("=========== 편의 메서드 적응 후 ==========");
        bem.execute(em -> {
            Team team1 = new Team("team1");
            em.persist(team1);


            Member member1 = new Member("member1");
            member1.setTeamForBiDire(team1);
            em.persist(member1);


            Member member2 = new Member("member2");
            member2.setTeamForBiDire(team1);
            em.persist(member2);
        });
    }

}
