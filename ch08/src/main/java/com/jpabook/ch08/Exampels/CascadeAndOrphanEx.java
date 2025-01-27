package com.jpabook.ch08.Exampels;

import com.jpabook.ch08.domain.Child;
import com.jpabook.ch08.domain.Parent;
import com.jpabook.ch08.manager.BaseEntityManager;

public class CascadeAndOrphanEx {

    private final static BaseEntityManager bem = new BaseEntityManager();


    public void cascadeType_All() {
        bem.execute(em -> {
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            //persist을 세번 호출해야함. -> 귀찮아...
            //나는 코드를 짤때 child를 parent가 관리했으면 좋겠어... -> cascade
//            em.persist(parent);
//            em.persist(child1);
//            em.persist(child2);
            //cascade 설정 안하면 에러남
            //cascade 시켜주면
            em.persist(parent);
            // 알아서 insert 쿼리 실행
            //Hibernate:
            //    /* insert for
            //        com.jpabook.ch08.domain.Parent */insert
            //    into
            //        Parent (name, id)
            //    values
            //        (?, default)
            //Hibernate:
            //    /* insert for
            //        com.jpabook.ch08.domain.Child */insert
            //    into
            //        Child (name, PARENT_ID, id)
            //    values
            //        (?, ?, default)
            //Hibernate:
            //    /* insert for
            //        com.jpabook.ch08.domain.Child */insert
            //    into
            //        Child (name, PARENT_ID, id)
            //    values
            //        (?, ?, default)

        });
    }

    public void orphanRemovalEx() {
        bem.execute(em -> {
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);
            em.persist(child1);
            em.persist(child2);

            em.flush();
            em.clear();

            System.out.println("!!!!!!!!!!!!!!!!!!!");

            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildren().remove(0);
            // orphanRemoval = true 로 해주면 delete 쿼리 날려짐
            //Hibernate:
            //    /* delete for com.jpabook.ch08.domain.Child */delete
            //    from
            //        Child
            //    where
            //        id=?
            //[info] : after Commit

            Child child3 = new Child();
            parent.addChild(child3);
            em.persist(child3);

            em.flush();
            em.clear();

            Parent findParent2 = em.find(Parent.class, parent.getId());
            em.remove(findParent2); // -> 자식까지 다 reomve 됨
            //Hibernate:
            //    /* delete for com.jpabook.ch08.domain.Child */delete
            //    from
            //        Child
            //    where
            //        id=?
            //Hibernate:
            //    /* delete for com.jpabook.ch08.domain.Child */delete
            //    from
            //        Child
            //    where
            //        id=?
            //Hibernate:
            //    /* delete for com.jpabook.ch08.domain.Parent */delete
            //    from
            //        Parent
            //    where
            //        id=?

        });
    }

    public void orphanRemoval_And_CaseCade() {
        bem.execute(em -> {
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());
            em.remove(findParent);

            //Parent 는 영속성 컨텍스트를 통해서 생명주기를 관리함
            // 그런데 child는 생명주기를 바로 parent가 관리함
        });
    }


}
