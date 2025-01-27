package com.jpabook.ch08;


import com.jpabook.ch08.Exampels.CascadeAndOrphanEx;
import com.jpabook.ch08.Exampels.LazyLoadEx;
import com.jpabook.ch08.Exampels.ProxyEx;


public class Ch08Main {

    public static void main(String[] args) {
        //프록시 예제
        proxyExamples();
        lazyLoadingExamples();
        cascadeAndOrphanExamples();

    }


    private static void proxyExamples() {
        ProxyEx proxyEx = new ProxyEx();
        proxyEx.referenceEx();
        proxyEx.referenceExInsctanceOf();
        proxyEx.영속성컨텍스트에_엔티티가존재하면_reference_호출시_실제_엔티티_반환();
        proxyEx.reference_다음_find_호출하면_find호출한객체는_reference의_프록시객체랑_같다();
        proxyEx.context_초기화시_예외_발생();
        proxyEx.프록시_인스턴스_초기화_여부_확인();
        proxyEx.프록시_강제_초기화();
        proxyEx.식별자_호출시_프록시_초기화_여부();
    }

    private static void lazyLoadingExamples() {
        LazyLoadEx lazyLoadEx = new LazyLoadEx();
        lazyLoadEx.lazyLoadEx();
        lazyLoadEx.FetachType을_EAGER로_설정_시();
        lazyLoadEx.jpqlFetchJoin();
        lazyLoadEx.proxy_collection_wrapper();
    }

    private static void cascadeAndOrphanExamples() {
        CascadeAndOrphanEx cascadeAndOrphanEx = new CascadeAndOrphanEx();
        cascadeAndOrphanEx.cascadeType_All();
        cascadeAndOrphanEx.orphanRemovalEx();
        cascadeAndOrphanEx.orphanRemoval_And_CaseCade();
    }

}
