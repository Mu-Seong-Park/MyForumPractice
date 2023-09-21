package hello.mylogin.controller;

import hello.mylogin.member.Member;
import hello.mylogin.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MemberController {

    private final MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/sign_up")
    public String addMemberForm(Model model) {
        model.addAttribute("member",new Member());
        return "member/addMemberForm";
    }

    @PostMapping("/sign_up")
    public String addMember(Model model, HttpServletRequest request) {
//        long id = Long.parseLong(request.getParameter("id"));
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");

        Member member = new Member();
        member.setEmail(email);
        member.setPassword(password);
        member.setName(name);
        member.setDeleted(false);

        Member addedMember = memberService.addMember(member);
        model.addAttribute("member",addedMember);
        return "redirect:/login";
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
