package com.jpabook.ch08.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    
    // jpa는 기본적으로 외부 조인 사용
    // 내부 조인을 사용하려면(null 값을 허용하지 않으면 사용해도 됨, 오히려 외부 조인보다 성능 좋음..) : nullable = false 지정 또는
    //@ManyToOne.optional = false로 설정하면 내부 조인을 사용
    
    //FetchType.LAZY 프록시 객체로 조회
    //FetchType.EAGER 즉시 로딩 : 디폴드
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TEAM_ID", nullable = false) //
    private Team team;


    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    public Member(String name, Team team) {
        this.name = name;
        this.team = team;
    }

    public Member() {
    }

    public void addOrder(Order order) {
        order.setMember(this);
    }

    @Override
    public String toString() {
        return "Member{" +
            "name='" + name + '\'' +
            ", id=" + id +
            '}';
    }
}
