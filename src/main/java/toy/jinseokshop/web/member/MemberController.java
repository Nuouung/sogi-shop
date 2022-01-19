package toy.jinseokshop.web.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toy.jinseokshop.domain.member.Member;
import toy.jinseokshop.domain.member.MemberConst;
import toy.jinseokshop.domain.member.MemberRepository;
import toy.jinseokshop.domain.member.MemberService;
import toy.jinseokshop.domain.order.OrderItem;
import toy.jinseokshop.domain.order.OrderService;
import toy.jinseokshop.web.login.SessionConst;
import toy.jinseokshop.web.login.SessionLoginDto;
import toy.jinseokshop.web.validator.MemberValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final OrderService orderService;
    private final MemberValidator memberValidator;

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("member", new MemberDto());
        return "member/joinForm";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute(name = "member") MemberDto memberDto, BindingResult bindingResult) {
        // 검증로직
        memberValidator.joinValidate(memberDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "member/joinForm";
        }

        Member member = new Member(memberDto);
        memberRepository.save(member);
        return "redirect:/main";
    }

    @GetMapping("/myInfo")
    public String myInfoPage(Model model, HttpServletRequest request) {
        SessionLoginDto sessionLoginDto = (SessionLoginDto) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);
        String email = sessionLoginDto.getEmail();
        MemberDto memberDto = memberService.findMemberDtoByEmail(email);

        if (memberDto == null) {
            return "/";
        }

        List<OrderItem> orderItems = orderService.findOrderItems(memberDto.getId());

        model.addAttribute("member", memberDto);
        model.addAttribute("items", orderItems);
        return "member/myInfo";
    }

    @GetMapping("/member/charge/{id}")
    public String chargePassion(@PathVariable Long id) {
        memberService.chargePassion(id);
        return "redirect:/myInfo";
    }

    @GetMapping("/find/password")
    public String findPasswordForm() {
        return "member/findPassword";
    }

//    @GetMapping("/find/password/find")
//    public String findPassword(@RequestParam String email, Model model) {
//        String result = memberService.findPassword(email);
//        model.addAttribute("result", result);
//        return "/member/findPassword";
//    }

}
