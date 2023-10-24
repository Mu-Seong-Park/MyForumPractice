package hello.mylogin.controller;

import hello.mylogin.config.SessionConst;
import hello.mylogin.forum.post.Post;
import hello.mylogin.forum.post.PostValidator;
import hello.mylogin.member.Member;
import hello.mylogin.service.ForumService;
import hello.mylogin.service.MemberService;
import hello.mylogin.service.PageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/forum")
public class ForumController {

    private final ForumService forumService;
    private final PostValidator postValidator;
    private final MemberService memberService;
    private final PageService pageService;
    public ForumController(ForumService forumService, PostValidator postValidator, MemberService memberService, PageService pageService) {
        this.forumService = forumService;
        this.postValidator = postValidator;
        this.memberService = memberService;
        this.pageService = pageService;
    }

    @GetMapping
    public String forumForm (Model model) {

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
    public String writePost (@ModelAttribute Post post, BindingResult bindingResult, HttpServletRequest request) {

        postValidator.validate(post,bindingResult);

        if(bindingResult.hasErrors()) {
            log.info("errors = {}",bindingResult);
            return "forum/writePostForm";
        }
        HttpSession session = request.getSession(false);

        if(session == null) {
            return "redirect:/login";
        }

        Member writer = ((Member) session.getAttribute(SessionConst.LOGIN_MEMBER));
        writer.thisPostBelongToMember(post);
        forumService.writePost(post);

        return "redirect:/forum";
    }

    @GetMapping("/post")
    public String readPost(@RequestParam("id") Long id , Model model, HttpServletRequest request) {

        Optional<Post> foundPost = forumService.findPostById(id);
        if(foundPost.isEmpty()) {
            return "redirect:/forum";
        }
        model.addAttribute("post",foundPost.get());
        HttpSession session = request.getSession(false);
        if(session == null) {
            return "redirect:/login";
        }

        Long loginMemberId = ((Member) session.getAttribute(SessionConst.LOGIN_MEMBER)).getId();
        model.addAttribute("loginMemberId",loginMemberId);
        model.addAttribute("writer",foundPost.get().getMember());
        return "forum/readPostForm";
    }

    @GetMapping("/update")
    public String updatePostForm(@RequestParam("id") Long id , Model model, HttpServletRequest request) {

        Optional<Post> foundPost = forumService.findPostById(id);
        if(foundPost.isEmpty()) {
            //포스트를 찾을 수 없는 경우, 자유게시판으로 redirect
            return "redirect:/forum";
        }
        log.info("작성자 : {}",foundPost.get().getMember().getName());
        model.addAttribute("post",foundPost.get());
        Member writer = memberService.findById(foundPost.get().getMember().getId());

        HttpSession session = request.getSession(false);

        if(session == null) {
            return "redirect:/login";
        }

        Long loginMemberId = ((Member) session.getAttribute(SessionConst.LOGIN_MEMBER)).getId();
        log.info("현재 로그인한 id : {}",loginMemberId);
        if(writer.getId() != loginMemberId) {
            //로그인 id 유효성 검사 로직.

        }
        return "forum/updatePostForm";
    }
    @PostMapping("/update")
    public String updatePost(@RequestParam("id") Long id , Post updatePost, RedirectAttributes redirectAttributes) {

        forumService.updatePost(id, updatePost);
        log.info("updatePost의 content : {}",updatePost.getContents());
        redirectAttributes.addAttribute("id", id);
        return "redirect:/forum/post";
    }

    @GetMapping("/delete")
    public String deletePost(@RequestParam("id") Long id , Model model, HttpServletRequest request) {

        Optional<Post> foundPost = forumService.findPostById(id);

        if(foundPost.isEmpty()) {
            //포스트를 찾을 수 없는 경우, 자유게시판으로 redirect
            return "redirect:/forum";
        }
        log.info("작성자 : {}",foundPost.get().getMember().getName());
        model.addAttribute("post",foundPost.get());
        Member writer = memberService.findById(foundPost.get().getMember().getId());

        HttpSession session = request.getSession();

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


        return "redirect:/forum";
    }

    @GetMapping("/search")
    public String searchPostForm(@RequestParam(value = "keyword",required = false) String keyword, Model model) {
        List<Post> searchPostList = forumService.searchPostByTitle(keyword);
        model.addAttribute("postList",searchPostList);
        return "forum/searchPostForm";
    }

}
