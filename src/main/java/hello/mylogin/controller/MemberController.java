package hello.mylogin.controller;

import hello.mylogin.member.Member;
import hello.mylogin.member.MemberDto;
import hello.mylogin.member.MemberValidator;
import hello.mylogin.member.Role;
import hello.mylogin.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/members")
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final MemberValidator memberValidator;

    public MemberController(MemberService memberService, MemberValidator memberValidator) {
        this.memberService = memberService;
        this.memberValidator = memberValidator;
    }

    @GetMapping("/sign_up")
    public String addMemberForm(Model model) {
        model.addAttribute("memberDto",new MemberDto());
        return "member/addMemberForm";
    }

    @PostMapping("/sign_up")
    public String addMember(@ModelAttribute MemberDto memberDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        memberValidator.validate(memberDto,bindingResult);

        if(bindingResult.hasErrors()) {
            log.info("errors = {}",bindingResult);
            return "member/addMemberForm";
        }

        Member member = new Member();
        member.setEmail(memberDto.getEmail());
        member.setPassword(memberDto.getPassword());
        member.setName(memberDto.getName());
        member.setDeleted(false);
        member.setRole(Role.BASIC);

        Optional<Member> addedMember = memberService.addMember(member);

        if(addedMember.isEmpty()) {
            bindingResult.addError(new FieldError("memberDto","email","이미 사용중인 ID(이메일)입니다."));
            return "member/addMemberForm";
        }

        return "redirect:/login";
    }

}
