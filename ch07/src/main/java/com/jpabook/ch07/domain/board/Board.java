package com.jpabook.ch07.domain.board;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;

@Entity
/**
 * 세컨더리
 */
@SecondaryTable(name = "BOARD_DETAIL_SE",
    pkJoinColumns = @PrimaryKeyJoinColumn(name = "BOARD_DETAIL_ID")
)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long id;

    private String title;

    // 일대일 식별 관계
//    @OneToOne(mappedBy = "board")
//    private BoardDetail boardDetail;

    //세컨더리
    @Column(table = "BOARD_DETAIL_SE")
    private String content;

    public Board(String title) {
        this.title = title;
    }

    public Board() {
    }
}
