package toy.jinseokshop.web.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import toy.jinseokshop.domain.member.Member;
import toy.jinseokshop.domain.member.MemberRepository;
import toy.jinseokshop.web.validator.MemberValidator;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberValidator memberValidator;

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("member", new MemberDto());
        return "/member/joinForm";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute(name = "member") MemberDto memberDto, BindingResult bindingResult) {
        // 검증로직
        memberValidator.joinValidate(memberDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/member/joinForm";
        }

        Member member = new Member(memberDto);
        memberRepository.save(member);
        return "redirect:/main";
    }

}
