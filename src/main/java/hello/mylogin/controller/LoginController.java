package hello.mylogin.controller;

import hello.mylogin.member.Member;
import hello.mylogin.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private final MemberService memberService;
    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("member",new Member());
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(Model model, HttpServletRequest request) {
//        long id = Long.parseLong(request.getParameter("id"));
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Member loginMember = memberService.findByEmail(email);
        if(loginMember.getPassword().equals(password)) {
            model.addAttribute("member",loginMember);
            return "redirect:/";
        }

        return "login/loginForm";
    }
}
