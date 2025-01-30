package com.jpabook.ch09;


import com.jpabook.ch09.domain.Address;
import com.jpabook.ch09.domain.AddressEntity;
import com.jpabook.ch09.domain.Member;
import com.jpabook.ch09.domain.Period;
import com.jpabook.ch09.manger.BaseEntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class Ch09Main {

    public static void main(String[] args) {
//        임베디드타입_예제();
//        임베디드_타입_여러엔티티_공유();
//        불변객체로();
//        값타입비교();
//        값타입_컬렉션_저장_조회();
//        값타입_컬렉션_수정();
//        값타입_컬렉션_대신_일대다_관계를_고려();
    }

    private static void 값타입_컬렉션_대신_일대다_관계를_고려() {
        BaseEntityManager bem = new BaseEntityManager();
        bem.execute(em ->{
            Member member = new Member("name");
            member.setHomeAddress(new Address("city", "street", "zipcode"));
            member.setWorkPeriod(new Period(LocalDateTime.now(), LocalDateTime.now()));

            member.setFavoriteFood(Set.of("치킨", "족발", "피자"));
            member.setAddressEntityList(List.of(
                new AddressEntity("old city1", "old street1", "old zipcode1"),
                new AddressEntity("old city2", "old street2", "old zipcode2")
            ));


            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("================= find =================");
            Member findMember = em.find(Member.class, member.getId());
            // 값 수정

            findMember.getAddressEntityList().remove(new AddressEntity("old city1", "old street1", "old zipcode1"));
            findMember.getAddressEntityList().add(new AddressEntity("new city1", "new street1", "new zipcode1"));
            //update
            //        ADDRESS
            //    set
            //        MEMBER_ID=?
            //    where
            //        id=?
            //[info] : after Commit
        });
    }

    private static void 값타입_컬렉션_수정() {
        BaseEntityManager bem = new BaseEntityManager();
        bem.execute(em ->{
            Member member = new Member("name");
            member.setHomeAddress(new Address("city", "street", "zipcode"));
            member.setWorkPeriod(new Period(LocalDateTime.now(), LocalDateTime.now()));

            member.setFavoriteFood(Set.of("치킨", "족발", "피자"));
            member.setAddressHistory(List.of(
                new Address("old city1", "old street1", "old zipcode1"),
                new Address("old city2", "old street2", "old zipcode2")
            ));


            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("================= Start =================");
            Member findMember = em.find(Member.class, member.getId());
            for (String s : findMember.getFavoriteFood()) {
                System.out.println("favorite food = " + s);
            }

            System.out.println("================= find =================");
            // 값 수정
            Address preAdd = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("new City", preAdd.getStreet(), preAdd.getZipcode()));

            // 값타입 컬랙션 수정
            // 치킨 -> 한식
            findMember.getFavoriteFood().remove("치킨");// delete
            //delete
            //    from
            //        FAVORITE_FOOD
            //    where
            //        MEMBER_ID=?
            //        and FOOD_NAME=?
            findMember.getFavoriteFood().add("한식"); //insert
            //insert
            //    into
            //        FAVORITE_FOOD (MEMBER_ID, FOOD_NAME)
            //    values
            //        (?, ?)

            // address history 변경
            //old1을 new1으로
            // remove 때 equals 로
            findMember.getAddressHistory().remove(new Address("old city1", "old street1", "old zipcode1"));
            findMember.getAddressHistory().add(new Address("new city1", "new street1", "new zipcode1"));
            //Hibernate:
            //    select
            //        ah1_0.MEMBER_ID,
            //        ah1_0.city,
            //        ah1_0.street,
            //        ah1_0.zipcode
            //    from
            //        ADDRESS_HISTORY ah1_0
            //    where
            //        ah1_0.MEMBER_ID=?
            // [info] : before Commit
            //Hibernate:   전체 MEMBER_ID에 해당하는 값 delete 후 다시 insert
            //    /* one-shot delete for addressHistory */delete
            //    from
            //        ADDRESS_HISTORY
            //    where
            //        MEMBER_ID=?
            //Hibernate:
            //    /* insert for
            //        com.jpabook.ch09.domain.Member.addressHistory */insert
            //    into
            //        ADDRESS_HISTORY (MEMBER_ID, city, street, zipcode)
            //    values
            //        (?, ?, ?, ?)
            //Hibernate:
            //    /* insert for
            //        com.jpabook.ch09.domain.Member.addressHistory */insert
            //    into
            //        ADDRESS_HISTORY (MEMBER_ID, city, street, zipcode)
            //    values
            //        (?, ?, ?, ?)
            //[info] : after Commit
            //테이블의 데이터를 완전히 갈아끼움...-> 하나만 delete 하고 insert 할 줄 알았는데....??
        });
    }

    private static void 값타입_컬렉션_저장_조회() {
        BaseEntityManager bem = new BaseEntityManager();
        bem.execute(em ->{
            Member member = new Member("name");
            member.setHomeAddress(new Address("new city", "new street", "new zipcode"));
            member.setWorkPeriod(new Period(LocalDateTime.now(), LocalDateTime.now()));

            member.setFavoriteFood(Set.of("치킨", "족발", "피자"));
            member.setAddressHistory(List.of(
                new Address("city1", "street2", "zipcode2"),
                new Address("city2", "street2", "zipcode2")
            ));


            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("================= Start =================");
            Member findMember = em.find(Member.class, member.getId());
            // 값 타입 컬랙션들은 지연 로딩 됨
            //Hibernate:
            //    select
            //        m1_0.id,
            //        m1_0.city,
            //        m1_0.street,
            //        m1_0.zipcode,
            //        m1_0.name,
            //        m1_0.endDate,
            //        m1_0.startDate
            //    from
            //        Member m1_0
            //    where
            //        m1_0.id=?
            System.out.println("================= find collections =================");
            List<Address> addressHistory = findMember.getAddressHistory();
            for (Address address : findMember.getAddressHistory()) {
                System.out.println(address.toString());
            }
        });
    }

    private static void 값타입비교() {
        int a = 10;
        int b = 10;
        System.out.println("(a == b) = " + (a == b));   // true;

        Address address1 = new Address("city1", "street1", "zipcode1");
        Address address2 = new Address("city1", "street1", "zipcode1");

        System.out.println("(address1 == address2) = " + (address1 == address2)); // false(애는 참조_
        System.out.println("address2.equals(address1) = " + address2.equals(address1)); // 오버라이드 안하면 false, 하면 true
    }

    private static void 불변객체로() {
        BaseEntityManager bem = new BaseEntityManager();
        bem.execute(em ->{
            Address address = new Address("city1", "street1", "zipcode1");

            Member member1 = new Member("member1");
            member1.setHomeAddress(address);
            em.persist(member1);


            //그럼 값을 바꾸고 싶을 때는??
            // 새로 만들어야함.....
            Address newAddress = new Address("new city1", address.getStreet(), address.getZipcode());
            member1.setHomeAddress(newAddress);

        });
    }

    private static void 임베디드_타입_여러엔티티_공유() {
        BaseEntityManager bem = new BaseEntityManager();
        bem.execute(em ->{
            Address address = new Address("city1", "street1", "zipcode1");

            Member member1 = new Member("member1");
            member1.setHomeAddress(address);
            em.persist(member1);

//            Member member2 = new Member("member2");
//            member2.setHomeAddress(address);
//            em.persist(member2);
//
//
//            //member1에도 같이 new city로 바뀜
//            member2.getHomeAddress().setCity("new City");

            //address 값을 복사를 해서
            Address copyAddress = new Address(address.getCity(), address.getStreet(),
                address.getZipcode());

            Member member2 = new Member("member2");
            member2.setHomeAddress(copyAddress);
            em.persist(member2);


        });
    }

    private static void 임베디드타입_예제() {
        BaseEntityManager bem = new BaseEntityManager();
        bem.execute(em ->{
            Member member = new Member();
            member.setName("name");
            member.setHomeAddress(new Address("city", "street", "zipcode"));
            member.setWorkPeriod(new Period(LocalDateTime.now(), LocalDateTime.now()));

            em.persist(member);
        });
    }
}
