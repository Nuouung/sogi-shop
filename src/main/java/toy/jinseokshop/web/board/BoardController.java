package toy.jinseokshop.web.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import toy.jinseokshop.domain.board.Board;
import toy.jinseokshop.domain.board.BoardRepository;
import toy.jinseokshop.domain.board.BoardService;
import toy.jinseokshop.web.validator.BoardValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;

    private final BoardValidator boardValidator;

    @GetMapping
    public String toBoard(@RequestParam int page, Model model) {
        List<Board> boardList = boardService.getPage(page);
        model.addAttribute("boardList", boardList);
        return "/board/boardList";
    }

    @GetMapping("/page/{boardId}")
    public String boardDetail(@PathVariable Long boardId, Model model, HttpServletRequest request) {
        Board board = boardRepository.findByBoardId(boardId)
                .orElse(null);

        if (board == null) {
            return "redirect:/board";
        }

        // 사용자가 글을 읽는다면 조회수를 하나 올린다.
        boardRepository.updateReadCount(boardId);

        String queryParam = getRefererQueryParameter(model, request);
        model.addAttribute("board", board);
        model.addAttribute("queryParam", queryParam);

        return "/board/boardDetail";
    }

    @GetMapping("/write")
    public String writeForm(Model model, HttpServletRequest request) {
        String queryParam = getRefererQueryParameter(model, request);
        model.addAttribute("board", new Board());
        model.addAttribute("queryParam", queryParam);

        return "/board/boardWriteForm";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute Board board, BindingResult bindingResult) {
        boardValidator.writeValidate(board, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/board/boardWriteForm";
        }

        Long boardId = boardService.saveWrite(board);
        return "redirect:/board/page/" + boardId;
    }

    @GetMapping("/page/{boardId}/update")
    public String updateForm(@PathVariable Long boardId, Model model) {
        Board board = boardRepository.findByBoardId(boardId).orElse(null);
        model.addAttribute("board", board);
        return "/board/boardUpdate";
    }

    @PostMapping("/page/{boardId}/update")
    public String update(@PathVariable Long boardId, @ModelAttribute Board updateParam) {
        boardRepository.update(boardId, updateParam);
        return "redirect:/board/page/" + boardId;
    }

    @GetMapping("/page/{boardId}/delete")
    public String delete(@PathVariable Long boardId) {
        boardRepository.delete(boardId);
        return "redirect:/board";
    }

    private String getRefererQueryParameter(Model model, HttpServletRequest request) {
        String[] arrayForURL = request.getHeader("referer").split("=");
        if (arrayForURL.length == 2) return arrayForURL[1];

        String firstPage = "1";
        return firstPage;
    }
}
