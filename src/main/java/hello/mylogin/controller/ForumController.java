package hello.mylogin.controller;

import hello.mylogin.config.SessionConst;
import hello.mylogin.forum.post.Post;
import hello.mylogin.forum.post.PostValidator;
import hello.mylogin.member.Member;
import hello.mylogin.service.ForumService;
import hello.mylogin.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequestMapping("/forum")
public class ForumController {

    private final ForumService forumService;
    private final PostValidator postValidator;
    private final MemberService memberService;
    public ForumController(ForumService forumService, PostValidator postValidator, MemberService memberService) {
        this.forumService = forumService;
        this.postValidator = postValidator;
        this.memberService = memberService;
    }

    @GetMapping
    public String freeForumForm (Model model) {

        model.addAttribute("postList",forumService.findAll());
        return "/forum/postList";
    }

    @GetMapping("/write")
    public String writePostForm (Model model){
        Post newPost = new Post();
        model.addAttribute("post",newPost);

        return "forum/writePostForm";
    }

    @PostMapping("/write")
    public String writePost (@ModelAttribute Post post, BindingResult bindingResult, Model model, HttpServletRequest request) {

        postValidator.validate(post,bindingResult);

        if(bindingResult.hasErrors()) {
            log.info("errors = {}",bindingResult);
            return "forum/writePostForm";
        }
        HttpSession session = request.getSession(false);

        if(session == null) {
            return "redirect:/login";
        }

        String loginMemberName = ((Member) session.getAttribute(SessionConst.LOGIN_MEMBER) ).getName();
        forumService.writePost(post,loginMemberName);

        return "redirect:/forum";
    }

    @GetMapping("/")
}
