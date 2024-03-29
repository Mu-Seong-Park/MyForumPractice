package hello.mylogin.controller;

import hello.mylogin.config.SessionConst;
import hello.mylogin.member.LoginDto;
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

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
        model.addAttribute("loginDto",new LoginDto());
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(Model model, @ModelAttribute LoginDto loginDto, BindingResult bindingResult, HttpServletRequest request) {
//        long id = Long.parseLong(request.getParameter("id"));

        Member loginMember = loginService.login(loginDto.getEmail(),loginDto.getPassword());


        if(loginMember == null) {
            bindingResult.reject("loginFail", "아이디 혹은 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        HttpSession session = request.getSession(true);
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
