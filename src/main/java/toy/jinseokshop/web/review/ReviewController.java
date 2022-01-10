package toy.jinseokshop.web.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import toy.jinseokshop.domain.review.ReviewService;
import toy.jinseokshop.web.login.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/item/detail")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{itemId}")
    public String submitReviewForm(@PathVariable Long itemId, @ModelAttribute ReviewDto reviewDto, HttpServletRequest request) {
        // 1. 어느 회원이 댓글을 썼는지 회원을 찾는다.
        String memberEmail = (String) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);

        // 2. review를 db상에 넣어야 한다.
        reviewService.save(memberEmail, itemId, reviewDto);
        return "redirect:/item/detail/" + itemId;
    }

}
