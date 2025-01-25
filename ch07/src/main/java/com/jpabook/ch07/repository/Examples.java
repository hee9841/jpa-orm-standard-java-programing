package com.jpabook.ch07.repository;




import com.jpabook.ch07.domain.Book;
import com.jpabook.ch07.domain.Movie;
import com.jpabook.ch07.domain.board.Board;
import com.jpabook.ch07.domain.board.BoardDetail;
import com.jpabook.ch07.domain.identifying.Child;
import com.jpabook.ch07.domain.identifying.Parent;
import com.jpabook.ch07.domain.identifying.ParentId;
import com.jpabook.ch07.manager.BaseEntityManager;
import java.time.LocalDateTime;

public class Examples {
    private final static BaseEntityManager bem = new BaseEntityManager();

    public void saveTest() {
        Movie movie = new Movie();
        movie.setDirector("director");
        movie.setActor("bbb");
        movie.setName("바람과함께사라지다.");
        movie.setPrice(10_000);
        movie.setCreateBy("user1");
        movie.setCreatedDateTime(LocalDateTime.now());
        movie.setLastModifiedBy("user1");
        movie.setLastModifiedDateTime(LocalDateTime.now());

        Book book = new Book();
        book.setAuthor("aaaaa");
        book.setIsbn("asdfasdf");
        book.setName("ㅜ믇");
        book.setPrice(12_000);

        bem.execute(em ->{
            em.persist(movie);
            em.persist(book);

            em.flush();
            em.clear();

            em.find(Movie.class, movie.getId());
            Book book1 = em.find(Book.class, book.getId());
            //조회하면 조인해서 가져옴
            //자식의 pk가 fk
        });
    }

    public void saveCompositeNonIdentify() {
        bem.execute(em -> {
            Parent parent = new Parent("parent");
            parent.setId(new ParentId(1L, 2L));
            em.persist(parent);
            em.flush();
            em.clear();

            Child child = new Child("child", parent);
            em.persist(child);

            em.flush();
            em.clear();

            Child child1 = em.find(Child.class, child.getId());

            System.out.println(child1);
        });
    }

    /**
     * 일대일 식별 관계 저장
     */
    public void saveBord() {
        bem.execute(em -> {
            Board board = new Board("제목");
            em.persist(board);

            BoardDetail boardDetail = new BoardDetail("content");
            boardDetail.setBoard(board);
            em.persist(boardDetail);

        });

    }
}
