package com.jpabook.ch07.domain.board;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import lombok.Setter;

@Entity
public class BoardDetail {
    @Id
    private Long boardId;

    @Setter
    @MapsId //BoardDtail.boradId 매핑
    @OneToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    private String content;

    public BoardDetail() {
    }

    public BoardDetail(String content) {
        this.content = content;
    }
}
