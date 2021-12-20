package toy.jinseokshop.web.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import toy.jinseokshop.domain.board.Board;

@Component
@Slf4j
public class BoardValidator {

    public void writeValidate(Board board, BindingResult bindingResult) {

        //1. 제목에는 빈값이 들어갈 수 없다.
        //2. 내용에는 빈값이 들어갈 수 없다.
        if (!StringUtils.hasText(board.getTitle())) {
//            bindingResult.addError(new FieldError("board", "title", board.getTitle(), false, new String[]{""}));
            bindingResult.addError(new FieldError("board", "title", "제목에는 빈 값이 들어갈 수 없습니다."));
        }

        if (!StringUtils.hasText(board.getContents())) {
            bindingResult.addError(new FieldError("board", "contents", "내용에는 빈 값이 들어갈 수 없습니다."));
        }

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
        }
    }
}
