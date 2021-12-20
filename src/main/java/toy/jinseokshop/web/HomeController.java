package toy.jinseokshop.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import toy.jinseokshop.domain.member.Member;
import toy.jinseokshop.web.login.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String toHome(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }

        String loginId = (String) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginId == null) {
            return "home";
        }

        model.addAttribute("member", loginId);
        return "home-login";
    }

}
