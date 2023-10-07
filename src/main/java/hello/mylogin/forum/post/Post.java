package hello.mylogin.forum.post;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Post {

    private Long id;

    private Long forumUserId;

    private String forumUserName;

    private String contents;

    private Date writtenDate;

    private boolean isDeleted;
}
