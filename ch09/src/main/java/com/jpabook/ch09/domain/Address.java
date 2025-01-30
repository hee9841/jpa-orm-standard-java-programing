package com.jpabook.ch09.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
//@Setter   // 불변 객체, 혹은 private으로 setter 만들 수 도
@Embeddable
public class Address {

    private String city;
    private String street;
    @Embedded
    private Zipcode zipcode;    //임베디드 타입 포함


    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = new Zipcode(zipcode, "0000");
    }

    public Address(String city, String street, Zipcode zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public Address() {
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Address address)) {
            return false;
        }
        return Objects.equals(city, address.city) && Objects.equals(street,
            address.street) && Objects.equals(zipcode, address.zipcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, zipcode);
    }
}
