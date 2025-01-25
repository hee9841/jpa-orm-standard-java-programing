package com.jpabook.ch07.domain.board;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

/**
 * 일대일 식별 관계(부모)
 */
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long id;

    private String title;

    // 일대일 식별 관계
    @OneToOne(mappedBy = "board")
    private BoardDetail boardDetail;

    public Board(String title) {
        this.title = title;
    }

    public Board() {
    }
}
