package com.jpabook;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        // 공장 만들기, 비용이 많이 듬 -> 애플리케이션 전체에 공유하도록 설계, 서로 다른 스래드 간 공유해도 됨
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        //cho02
//        new Ch02Ex().logic_ex1(emf);
        //ch03
        callCh3Ex(emf);


        emf.close();
    }

    private static void callCh3Ex(EntityManagerFactory emf) {
        Ch3LogicsExample ch3Ex = new Ch3LogicsExample(emf);
        ch3Ex.entityLifeCycle(1L);
        ch3Ex.firstCache(2L);
        ch3Ex.notInFistCache(3L);
        ch3Ex.entityIdentity(4L);
        ch3Ex.transactionWriteBehind(5L, 6L);
        ch3Ex.dirtyChecking(7L);
        ch3Ex.flushEx(8L);
        ch3Ex.detachedEx(9L);
        ch3Ex.clear(10L);
        ch3Ex.merge(11L);
    }

}
