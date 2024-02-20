package hello.mylogin;

import hello.mylogin.config.SessionConst;
import hello.mylogin.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);

        if(session == null) {
            log.info("세션이 존재하지 않습니다.");
            return "home";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if(loginMember == null) {
            log.info("회원이 존재하지 않습니다.");
            return "home";
        }


        model.addAttribute("member",loginMember);
        log.info("http session : {}",session.getId());
        return "loginHome";
    }


}
