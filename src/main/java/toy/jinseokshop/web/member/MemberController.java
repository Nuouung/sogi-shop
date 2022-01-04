package toy.jinseokshop.web.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import toy.jinseokshop.domain.member.Member;
import toy.jinseokshop.domain.member.MemberRepository;
import toy.jinseokshop.web.validator.MemberValidator;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberValidator memberValidator;

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("member", new Member());
        return "/member/joinForm";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute Member member, BindingResult bindingResult) {
        // 검증로직
        memberValidator.joinValidate(member, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/member/joinForm";
        }

        memberRepository.save(member);
        return "redirect:/main";
    }

}
