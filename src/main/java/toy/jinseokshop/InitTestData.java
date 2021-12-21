package toy.jinseokshop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import toy.jinseokshop.domain.board.Board;
import toy.jinseokshop.domain.board.BoardRepository;
import toy.jinseokshop.domain.board.BoardService;
import toy.jinseokshop.domain.member.Member;
import toy.jinseokshop.domain.member.MemberRepository;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitTestData {

    private final BoardService boardService;
    private final MemberRepository memberRepository;

    @PostConstruct
    public void addInitData() {
        for (int i = 1; i < 51; i++) {
            Board board = new Board();
            board.setTitle("테스트 제목" + i);
            board.setContents("테스트 내용" + i);
            board.setWriter("아이엠 테스터");

            boardService.saveWrite(board);
        }
    }

    @PostConstruct
    public void initMember() {
        Member member = new Member();
        member.setUserId("iAmAdmin");
        member.setPassword("123456");

        memberRepository.save(member);
    }
}
