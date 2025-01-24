package com.jpabook.ch06.domain;

import java.io.Serializable;

/**
 * 회원상품 식별자 클래스
 * 복합키
 */
public class OrderId implements Serializable {
    private Long member;
    private Long product;

    //hasCode and equals 구현
    @Override
    public boolean equals(Object obj) {
        //...
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        //..
        return super.hashCode();
    }
}
