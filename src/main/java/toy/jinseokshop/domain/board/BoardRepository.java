package toy.jinseokshop.domain.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy.jinseokshop.domain.board.Board;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    // c
    public Long save(Board board) {
        em.persist(board);
        return board.getBoardId();
    }

    // r
    public Optional<Board> findByBoardId(Long boardId) {
        return em.createQuery("select b from Board b where b.boardId = :boardId", Board.class)
                .setParameter("boardId", boardId)
                .getResultStream()
                .findAny();
    }

    public List<Board> findPage(int startPosition, int maxResult) {
        return em.createQuery("select b from Board b order by b.boardId desc", Board.class)
                .setFirstResult(startPosition)
                .setMaxResults(maxResult)
                .getResultList();
    }

    // u
    public void update(Long boardId, Board updateParam) {
        Board board = em.find(Board.class, boardId);

        if (board == null) {
            return;
        }

        board.setTitle(updateParam.getTitle());
        board.setContents(updateParam.getContents());
        board.setUpdateDate(LocalDateTime.now());
    }

    public void updateReadCount(Long boardId) {
        Board board = em.find(Board.class, boardId);

        if (board == null) {
            return;
        }

        board.setReadCount(board.getReadCount() + 1);
    }

    // d
    public void delete(Long boardId) {
        Board board = em.find(Board.class, boardId);
        em.remove(board);
    }
}
