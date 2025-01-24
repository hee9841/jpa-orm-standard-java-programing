package com.jpabook.ch06.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    @GeneratedValue
    private Long id;

    private String userName;

    //1:1 (주테이블에 외래키)
    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    //1:1 (대상테이블에 외래키, -> 양방향 + 대상테이블 주인)
//    @OneToOne(mappedBy = "member")
//    private Locker locker;

    //1:N (단방향은 필드 없음)
    //1:N(양방향) : joinColum으로 읽기전용으로 만듬
//    @ManyToOne
//    @JoinColumn(name = "TEAM_ID",
//        insertable = false,
//        updatable = false
//    )
//    private Team team;

    // N:1 양방향
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    //N:M -> 연결 테이블을 만듬
//    @ManyToMany
//    @JoinTable(name = "TEMP_MEMBER_PRODUCT",
//        joinColumns = @JoinColumn(name = "MEMBER_ID"),
//        inverseJoinColumns = @JoinColumn(name = "PRODCUT_ID")
//    )
//    private List<Product> products;
//

    //1:N 으로 Orders라는 테이블 추가
    @OneToMany(mappedBy = "member") //주인으로 하기 힘드니 역방향으로(양방향으로 풀기)
    private final List<Order> orders = new ArrayList<>();


    @Builder
    public Member(String userName) {
        this.userName = userName;
    }


    public void setTeam(Team team) {
        //내 객체의 team이 null이 아니면
        if (this.team != null) {
            this.team.getMembers().remove(this);
        }
        this.team = team;
        this.team.getMembers().add(this);
    }


    @Override
    public String toString() {
        return "Member{" +
            "id=" + id +
            ", userName='" + userName + '\'' +
            ", locker=" + locker +
            '}';
    }
}
