package toy.jinseokshop.web.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import toy.jinseokshop.domain.login.LoginService;
import toy.jinseokshop.domain.member.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "login/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginDto loginDto, BindingResult bindingResult, HttpServletRequest request) {

        Member member = loginService.login(loginDto.getEmail(), loginDto.getPassword());

        // 인증
        if (!StringUtils.hasText(member.getEmail())) {
            // 로그인 실패
            bindingResult.addError(new ObjectError("loginDto", new String[]{"아이디나 비밀번호가 올바르지 않습니다."}, new String[]{}, null));
            log.info("errors = {}", bindingResult);
            return "login/login";
        }

        // 로그인 성공 - 인가
        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_MEMBER, new SessionLoginDto(member.getEmail(), member.getIsSeller()));

        return "redirect:/main";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }
}
