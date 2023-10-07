package hello.mylogin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/forum")
public class ForumController {

    @GetMapping
    public String freeForumForm () {

        return "/forum/forumList";
    }

}
