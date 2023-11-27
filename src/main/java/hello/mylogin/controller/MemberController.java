package hello.mylogin.controller;

import hello.mylogin.config.SessionConst;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
        member.setRole(Role.USER);

        Optional<Member> addedMember = memberService.addMember(member);

        if(addedMember.isEmpty()) {
            bindingResult.addError(new FieldError("memberDto","email","이미 사용중인 ID(이메일)입니다."));
            return "member/addMemberForm";
        }

        return "redirect:/login";
    }

    @GetMapping("/info")
    public String memberInfoForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        model.addAttribute("member",loginMember);
        return "member/memberInfoForm";
    }

    @GetMapping("/edit")
    public String memberEditForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("member",loginMember);
        return "/member/editMemberInfoForm";

    }

    @PostMapping("/edit")
    public String memberEdit(Member updateMember, RedirectAttributes redirectAttributes,HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        memberService.updateMember(loginMember.getId(), updateMember);
        redirectAttributes.addAttribute("member", loginMember);
        return "redirect:/members/info";
    }
}
