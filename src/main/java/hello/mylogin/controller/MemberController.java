package hello.mylogin.controller;

import hello.mylogin.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;

public class MemberController {

    private final MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("")
    public void addMember() {

    }


}
