package hello.mylogin.member;

import hello.mylogin.forum.post.Post;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 40)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @CreationTimestamp
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    //Post 객체와 바인드하기 위한 메서드.
    public void thisPostBelongToMember(Post post) {
        post.setMember(this);
        this.posts.add(post);
    }
}
