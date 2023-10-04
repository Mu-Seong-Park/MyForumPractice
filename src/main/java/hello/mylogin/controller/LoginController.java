package hello.mylogin.controller;

import hello.mylogin.config.SessionConst;
import hello.mylogin.member.Member;
import hello.mylogin.service.LoginService;
import hello.mylogin.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final MemberService memberService;
    private final LoginService loginService;
    public LoginController(MemberService memberService, LoginService loginService) {
        this.memberService = memberService;
        this.loginService = loginService;
    }


    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("member",new Member());
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(Model model,@ModelAttribute String email,
                        @ModelAttribute String password, BindingResult bindingResult, HttpServletRequest request) {
//        long id = Long.parseLong(request.getParameter("id"));

        Member loginMember = loginService.login(email,password);

        if(loginMember == null) {
            bindingResult.reject("loginFail", "아이디 혹은 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        HttpSession session = request.getSession();

        session.setAttribute(SessionConst.LOGIN_MEMBER,loginMember);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }
}
