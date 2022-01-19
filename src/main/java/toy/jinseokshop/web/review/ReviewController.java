package toy.jinseokshop.web.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import toy.jinseokshop.domain.review.ReviewService;
import toy.jinseokshop.web.login.SessionConst;
import toy.jinseokshop.web.login.SessionLoginDto;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/item/detail")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{itemId}")
    public String submitReviewForm(@PathVariable Long itemId, @ModelAttribute ReviewFormDto reviewFormDto, HttpServletRequest request) {
        // 1. 어느 회원이 댓글을 썼는지 회원을 찾는다.
        SessionLoginDto sessionLoginDto = (SessionLoginDto) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);
        String memberEmail = sessionLoginDto.getEmail();

        // 2. review를 db상에 넣어야 한다.
        reviewService.save(memberEmail, itemId, reviewFormDto);
        return "redirect:/item/detail/" + itemId;
    }

}
