package toy.jinseokshop.domain.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    /**
     * 글을 쓰면 readCount, createdDate, updateDate를 만들어준다.
     */
    public Long saveWrite(Board board) {
        board.setReadCount(0);
        board.setCreatedDate(LocalDateTime.now());
        board.setUpdateDate(LocalDateTime.now());

        Long boardId = boardRepository.save(board);
        return boardId;

    }

    /**
     * 페이징 기능
     */
    public List<Board> getPage(int index) {
        int maxResult = 12;
        int startPosition = (index - 1) * maxResult;

        List<Board> page = boardRepository.findPage(startPosition, maxResult);

        return page;
    }
}
