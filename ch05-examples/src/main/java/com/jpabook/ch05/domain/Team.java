package com.jpabook.ch05.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Team {
    @Id
    @Column(name = "TEAM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    //==양방향 매핑을 위해 추가==//
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<Member>();
    //==양방향 매핑을 위해 추가==//


    @Builder
    public Team(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Builder
    public Team(String name) {
        this.name = name;
    }
}
