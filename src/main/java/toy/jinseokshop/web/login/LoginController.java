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
        return "/login/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginDto loginDto, BindingResult bindingResult, HttpServletRequest request) {

        String loginEmail = loginService.login(loginDto.getEmail(), loginDto.getPassword());

        // 인증
        if (!StringUtils.hasText(loginEmail)) {
            // 로그인 실패
            bindingResult.addError(new ObjectError("loginDto", new String[]{"아이디나 비밀번호가 올바르지 않습니다."}, new String[]{}, null));
            log.info("errors = {}", bindingResult);
            return "/login/loginForm";
        }

        // 로그인 성공 - 인가
        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginEmail);

        return "redirect:/main"; // TODO 후에 요청 request로 이동하는 기능
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
