package hello.mylogin.controller;

import hello.mylogin.service.ForumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/forum")
public class ForumController {

    private final ForumService forumService;

    public ForumController(ForumService forumService) {
        this.forumService = forumService;
    }

    @GetMapping
    public String freeForumForm (Model model) {

        model.addAttribute("postList",forumService.findAll());
        return "/forum/postList";
    }

}
