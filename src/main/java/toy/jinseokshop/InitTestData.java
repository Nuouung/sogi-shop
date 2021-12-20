package toy.jinseokshop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import toy.jinseokshop.domain.board.Board;
import toy.jinseokshop.domain.board.BoardRepository;
import toy.jinseokshop.domain.board.BoardService;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitTestData {

    private final BoardService boardService;

    /*@PostConstruct
    public void addInitData() {
        for (int i = 1; i < 51; i++) {
            Board board = new Board();
            board.setTitle("테스트 제목" + i);
            board.setContents("테스트 내용" + i);
            switch (i % 4) {
                case 0:
                    board.setWriter("피리부는 사나이");
                    break;
                case 1:
                    board.setWriter("나는야 코코더");
                    break;
                case 2:
                    board.setWriter("티알피지 조아");
                    break;
                case 3:
                    board.setWriter("나도 봄");
            }

            boardService.saveWrite(board);
        }
    }*/
}
