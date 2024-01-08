package hello.mylogin.forum.post;

import hello.mylogin.member.Member;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "forumUserId", referencedColumnName = "id")
    private Member member;

//    @Column(nullable = false)
//    private String forumUserName;

    @Column(nullable = false, length = 30)
    private String title;

    @Column
    private String contents;

    @CreationTimestamp
    private LocalDateTime writtenDate;

    @UpdateTimestamp // 업데이트(update) 쿼리가 날아갈 때마다 시간 갱신.
    private LocalDateTime updateDate;

    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;

    @Column(columnDefinition = "boolean default false")
    private boolean isUpdated;
}
