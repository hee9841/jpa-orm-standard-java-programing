package com.jpabook.ch07.domain.identifying;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ParentId implements Serializable {

    @Column(name = "PARENT_ID1")
    private Long id1;   //Parent.id1 매핑

    @Column(name = "PARENT_ID2")
    private Long id2;   //Parent.id2 매핑

    public ParentId() {
    }

    public ParentId(Long id1, Long id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

//식별자 클래스인 ParentId를 사용해 조회
 //    select
 //        p1_0.PARENT_ID1,
 //        p1_0.PARENT_ID2,
 //        p1_0.name
 //    from
 //        Parent p1_0
 //    where
 //        (
 //            p1_0.PARENT_ID1, p1_0.PARENT_ID2
 //        ) in ((?, ?))
