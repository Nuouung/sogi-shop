//package toy.jinseokshop.web.board;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import toy.jinseokshop.domain.board.Board;
//import toy.jinseokshop.domain.board.BoardRepository;
//import toy.jinseokshop.domain.board.BoardService;
//import toy.jinseokshop.domain.file.*;
//import toy.jinseokshop.web.file.FileFormDto;
//import toy.jinseokshop.web.file.FileStorageManager;
//import toy.jinseokshop.web.login.SessionConst;
//import toy.jinseokshop.web.validator.BoardValidator;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//
//@Controller
//@RequestMapping("/board")
//@RequiredArgsConstructor
//@Slf4j
//public class BoardController {
//
//    private final BoardRepository boardRepository;
//    private final BoardService boardService;
//
//    private final FileStorageManager fileStorageManager;
//    private final FileService fileService;
//
//    private final BoardValidator boardValidator;
//
//    @GetMapping
//    public String toBoard(@RequestParam int page, Model model) {
//        Map<String, Object> pageMap = boardService.getPage(page);
//        int totalPage = (Integer) pageMap.get("totalPage");
//        List<?> boardList = new ArrayList<>((Collection<?>) pageMap.get("boardList"));
//
//        model.addAttribute("boardList", boardList);
//        model.addAttribute("page", page);
//        model.addAttribute("totalPage", totalPage);
//        return "/board/boardList";
//    }
//
//    @GetMapping("/page/{boardId}")
//    public String boardDetail(@PathVariable Long boardId, Model model, HttpServletRequest request) {
//        Board board = boardRepository.findByBoardId(boardId)
//                .orElse(null);
//
//        FileViewDto fileViewDto = fileService.findByBoardId(boardId);
//
//        if (board == null) {
//            return "redirect:/board";
//        }
//
//        // 사용자가 글을 읽는다면 조회수를 하나 올린다.
//        boardRepository.updateReadCount(boardId);
//
//        String queryParam = getRefererQueryParameter(model, request);
//        model.addAttribute("board", board);
//        model.addAttribute("file", fileViewDto);
//        model.addAttribute("queryParam", queryParam);
//
//        for (MultipleFiles file : fileViewDto.getFiles()) {
//            System.out.println(file.getFile().getOriginalFileName());
//        }
//
//        return "/board/boardDetail";
//    }
//
//    @GetMapping("/write")
//    public String writeForm(Model model, HttpServletRequest request) {
//        String queryParam = getRefererQueryParameter(model, request);
//        model.addAttribute("board", new Board());
//        model.addAttribute("queryParam", queryParam);
//
//        return "/board/boardWriteForm";
//    }
//
//    @PostMapping("/write")
//    public String write(@ModelAttribute Board board, BindingResult bindingResult, HttpServletRequest request, @ModelAttribute FileFormDto fileDto) throws IOException {
//        boardValidator.writeValidate(board, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return "/board/boardWriteForm";
//        }
//
//        Object userId = request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);
//        board.setWriter((String) userId);
//
//        Long boardId = boardService.saveWrite(board);
//        fileService.save(fileTransformationForStorage(board, fileDto));
//        return "redirect:/board/page/" + boardId;
//    }
//
//    @GetMapping("/page/{boardId}/update")
//    public String updateForm(@PathVariable Long boardId, Model model) {
//        Board board = boardRepository.findByBoardId(boardId).orElse(null);
//        model.addAttribute("board", board);
//        return "/board/boardUpdate";
//    }
//
//    @PostMapping("/page/{boardId}/update")
//    public String update(@PathVariable Long boardId, @ModelAttribute Board updateParam) {
//        boardRepository.update(boardId, updateParam);
//        return "redirect:/board/page/" + boardId;
//    }
//
//    @GetMapping("/page/{boardId}/delete")
//    public String delete(@PathVariable Long boardId) {
//        boardRepository.delete(boardId);
//        return "redirect:/board";
//    }
//
//    private File fileTransformationForStorage(Board board, FileFormDto fileDto) throws IOException {
//        UploadFile uploadFile = fileStorageManager.store(fileDto.getFile());
//        List<UploadFile> uploadFileList = fileStorageManager.store(fileDto.getFiles());
//        return File.storeFile(board, uploadFile, uploadFileList);
//    }
//
//    private String getRefererQueryParameter(Model model, HttpServletRequest request) {
//        String referer = request.getHeader("referer");
//        if (referer != null) {
//            String[] arrayForURL = referer.split("=");
//            if (arrayForURL.length == 2) return arrayForURL[1];
//        }
//        return "1";
//    }
//}
