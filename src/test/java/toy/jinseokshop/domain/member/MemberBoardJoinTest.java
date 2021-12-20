package toy.jinseokshop.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import toy.jinseokshop.domain.board.Board;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberBoardJoinTest {

    @Autowired
    EntityManager em;

    @Test
    void 되려나() {
        Member member = new Member();
        member.setUserId("userA");

        em.persist(member);
    }

    @Test
    void 오된다그다음() {
        Member member = new Member();
        member.setUserId("userA");

        Board board1 = new Board();
        Board board2 = new Board();
        board1.setTitle("바나나가 맛있다");
        board2.setTitle("사과가 더 맛있다");

        board1.setMember(member);
        board2.setMember(member);

        em.persist(member);
        em.persist(board1);
        em.persist(board2);

        System.out.println(board1.getMember().getUserId()); // userA
        // 흥미롭다. userA가 먼저 출력되고, 이후에 insert문이 실행된다.
    }

    @Test
    void 그렇다면이건() {
        Member member = new Member();
        member.setUserId("userA");

        Board board1 = new Board();
        Board board2 = new Board();
        board1.setTitle("바나나가 맛있다");
        board2.setTitle("사과가 더 맛있다");

        board1.setMember(member);
        board2.setMember(member);

        // fk로 묶는 member 객체 생성 없이 board 먼저 만듬
        em.persist(board1);
        em.persist(board2);
        em.persist(member);
        // 결과 성공. 쿼리가 되게 이상하게 나간다 대신. 싱기
    }

    @Test
    void 조인테스트() {
        Member member = new Member();
        member.setUserId("userA");

        Board board1 = new Board();
        Board board2 = new Board();
        board1.setTitle("바나나가 맛있다");
        board2.setTitle("사과가 더 맛있다");

        board1.setMember(member);
        board2.setMember(member);

        em.persist(member);
        em.persist(board1);
        em.persist(board2);

        String jpql = "select b from Board b join b.member m where m.id = 1";
        List<Board> resultList = em.createQuery(jpql, Board.class)
                .getResultList();

        Assertions.assertThat(resultList).hasSize(2);
    }

}