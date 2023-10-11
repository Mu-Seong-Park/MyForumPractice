package hello.mylogin.forum.post;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class Post {

    private Long id;

    private Long forumUserId;

    private String forumUserName;

    private String title;

    private String contents;

    private LocalDateTime writtenDate;

    private LocalDateTime updateDate;

    private boolean isDeleted;
}
