package hello.mylogin.controller;

import hello.mylogin.member.Member;
import hello.mylogin.member.MemberDto;
import hello.mylogin.member.Role;
import hello.mylogin.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/members")
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
    public String addMember(@ModelAttribute MemberDto memberDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(!StringUtils.hasText(memberDto.getEmail()) || !StringUtils.hasText(memberDto.getPassword()) || !StringUtils.hasText(memberDto.getName())) {
            bindingResult.addError(new FieldError("member","member"));
        }

        Member member = new Member();
        member.setEmail(memberDto.getEmail());
        member.setPassword(memberDto.getPassword());
        member.setName(memberDto.getName());
        member.setDeleted(false);
        member.setRole(Role.BASIC);

        Member addedMember = memberService.addMember(member);
        if(addedMember == null) {
            return "member/addMemberForm";
        }

        model.addAttribute("member",addedMember);
        return "redirect:/login";
    }

}
