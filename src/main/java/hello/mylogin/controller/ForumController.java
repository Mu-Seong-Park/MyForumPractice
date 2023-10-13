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
import org.springframework.web.bind.annotation.*;

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
        Long loginMemberId = ((Member) session.getAttribute(SessionConst.LOGIN_MEMBER)).getId();
        forumService.writePost(post,loginMemberId, loginMemberName);

        return "redirect:/forum";
    }

    @GetMapping("/post")
    public String readPost(@RequestParam("id") Long id , Model model, HttpServletRequest request) {

        Post foundPost = forumService.findPostById(id);
        model.addAttribute("post",foundPost);
        HttpSession session = request.getSession(false);
        if(session == null) {
            return "redirect:/login";
        }

        Long loginMemberId = ((Member) session.getAttribute(SessionConst.LOGIN_MEMBER)).getId();
        model.addAttribute("writerId",loginMemberId);
        return "forum/readPostForm";
    }

    @GetMapping("/update")
    public String updatePostForm(@RequestParam("id") Long id , Model model, HttpServletRequest request) {

        Post foundPost = forumService.findPostById(id);
        log.info("작성자 : {}",foundPost.getForumUserId());
        model.addAttribute("post",foundPost);
        Member writer = memberService.findById(foundPost.getForumUserId());

        HttpSession session = request.getSession(false);

        if(session == null) {
            return "redirect:/login";
        }

        Long loginMemberId = ((Member) session.getAttribute(SessionConst.LOGIN_MEMBER)).getId();
        log.info("현재 로그인한 id : {}",loginMemberId);
        if(writer.getId() != loginMemberId)
        {
            //로그인 id 유효성 검사 로직.

        }
        return "forum/updatePostForm";
    }
    @PostMapping("/update")
    public String updatePost(@RequestParam("id") Long id , Post updatePost) {

        forumService.updatePost(id, updatePost);
        log.info("updatePost의 content : {}",updatePost.getContents());
        return "forum/readPostForm";
    }

    @GetMapping("/delete")
    public String deletePost(@RequestParam("id") Long id , Model model, HttpServletRequest request) {

        Post foundPost = forumService.findPostById(id);
        Member writer = memberService.findById(foundPost.getForumUserId());

        HttpSession session = request.getSession(false);

        if(session == null) {
            return "redirect:/login";
        }

        Long loginMemberId = ((Member) session.getAttribute(SessionConst.LOGIN_MEMBER)).getId();
        log.info("현재 로그인한 id : {}",loginMemberId);
        if(writer.getId() != loginMemberId)
        {
            //로그인 id 유효성 검사 로직.
            //다르다면 접근권한 없다고 팝업 띄우고 리스트로.

        }

        forumService.deletePost(id, loginMemberId);


        return "forum/postList";
    }
}
