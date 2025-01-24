package com.jpabook.ch06.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    @GeneratedValue
    private Long id;
    private String name;

    //1:N
    @OneToMany
    @JoinColumn(name = "TEAM_ID")
    private List<Member> members = new ArrayList<>();

    //N:1 양방향
//    @OneToMany(mappedBy = "team")
//    private List<Member> members;

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
