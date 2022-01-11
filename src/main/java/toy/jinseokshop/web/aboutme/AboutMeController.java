package toy.jinseokshop.web.aboutme;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutMeController {

    @GetMapping("/about-me")
    public String aboutMePage() {
        return "/aboutme/aboutme";
    }

}