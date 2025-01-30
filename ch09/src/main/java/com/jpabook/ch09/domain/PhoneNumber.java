package com.jpabook.ch09.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

@Embeddable
public class PhoneNumber {
    String areaCode;
    String localNumber;
    @ManyToOne
    PhoneServiceProvider provider;  // 엔티티 참조
}
