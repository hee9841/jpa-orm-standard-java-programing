package com.jpabook.ch05.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    @Setter
    @ManyToOne  //N:1, 회원과 팀은 다대일 관계임으로, 연관관계를 맵핑할 때 다중성을 나타내는 어노테이션 필수 사용 
    @JoinColumn(name = "TEAM_ID")   //외래키를 매핑할 때 사용, 매핑할 외래키 이름을 지정, 해당 어노테이션 생략 가능
    private Team team;

    public void setTeamForBiDire(Team team) {
        //기존 팀과 관계를 제거
        if (this.team != null) {
            this.team.getMembers().remove(this);
        }
        this.team = team;
        team.getMembers().add(this);
    }

    @Builder
    public Member(String userName, Team team) {
        this.userName = userName;
        this.team = team;
    }

    @Builder
    public Member(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Member{" +
            "id=" + id +
            ", userName='" + userName + '\'' +
            ", teamName=" + team.getName() +
            '}';
    }
}
