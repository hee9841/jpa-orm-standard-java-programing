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
        ch3Ex.entityLifeCycle();
        ch3Ex.firstCache();
        ch3Ex.notInFistCache();
        ch3Ex.entityIdentity();
        ch3Ex.transactionWriteBehind();
        ch3Ex.dirtyChecking();
        ch3Ex.flushEx();
        ch3Ex.detachedEx();
        ch3Ex.clear();
        ch3Ex.merge();
    }

}
