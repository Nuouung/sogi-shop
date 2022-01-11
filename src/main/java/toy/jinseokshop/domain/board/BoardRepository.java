//package toy.jinseokshop.domain.board;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//import toy.jinseokshop.domain.board.Board;
//
//import javax.persistence.EntityManager;
//import javax.persistence.TypedQuery;
//import javax.transaction.Transactional;
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@Repository
//@Transactional
//@RequiredArgsConstructor
//public class BoardRepository {
//
//    private final EntityManager em;
//
//    // c
//    public Long save(Board board) {
//        em.persist(board);
//        return board.getBoardId();
//    }
//
//    // r
//    public Optional<Board> findByBoardId(Long boardId) {
//        return em.createQuery("select b from Board b where b.boardId = :boardId", Board.class)
//                .setParameter("boardId", boardId)
//                .getResultStream()
//                .findAny();
//    }
//
//    public Map<String, Object> findPage(int startPosition, int maxResult) {
//        TypedQuery<Board> queryResult = em.createQuery("select b from Board b order by b.boardId desc", Board.class);
//        List<Board> tempList = queryResult.getResultList();
//
//        // 1. 총 페이지 개수를 구하기 위해 resultList를 조작한다.
//        int totalPage = tempList.size() / maxResult;
//
//        if (tempList.size() % maxResult != 0) {
//            totalPage += 1;
//        }
//
//        // 2. 사용자가 요청한 페이지에 존재하는 게시글을 추출한다.
//        List<Board> resultList = queryResult
//                .setFirstResult(startPosition)
//                .setMaxResults(maxResult)
//                .getResultList();
//
//        // 3. 1의 결과와 2의 결과를 합쳐 map에 담고 반환
//        Map<String, Object> pageMap = new HashMap<>();
//        pageMap.put("totalPage", totalPage);
//        pageMap.put("boardList", resultList);
//
//        return pageMap;
//    }
//
//    // u
//    public void update(Long boardId, Board updateParam) {
//        Board board = em.find(Board.class, boardId);
//
//        if (board == null) {
//            return;
//        }
//
//        board.setTitle(updateParam.getTitle());
//        board.setContents(updateParam.getContents());
//        board.setUpdateDate(LocalDateTime.now());
//    }
//
//    public void updateReadCount(Long boardId) {
//        Board board = em.find(Board.class, boardId);
//
//        if (board == null) {
//            return;
//        }
//
//        board.setReadCount(board.getReadCount() + 1);
//    }
//
//    // d
//    public void delete(Long boardId) {
//        Board board = em.find(Board.class, boardId);
//        em.remove(board);
//    }
//}
