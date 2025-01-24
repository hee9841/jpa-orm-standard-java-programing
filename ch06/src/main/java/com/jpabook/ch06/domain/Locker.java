package com.jpabook.ch06.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Locker {

    @Id
    @GeneratedValue
    private Long id;

    String name;

    //1:1 양방향(주테이블에 외래키)
    @OneToOne(mappedBy = "locker")
    private Member member;

    //1:1 (대상테이블에 외래키), 단방향은 지원X,
    // 추천 X
    // 단방향 관계를 Locker에서 Member 방향으로 수정 or
    // 양방양 관계로 하고 Locker를 주인으로
//    @OneToOne(mappedBy = "locker")
//    @JoinColumn(name = "MEMBER_ID")
//    private Member member;

}
//todo 런어스 챌린지랑...이런거 일대일 -> running쪽에 fk로 설정
