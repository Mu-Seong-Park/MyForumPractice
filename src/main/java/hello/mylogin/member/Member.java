package hello.mylogin.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {

    private Long id;

    private String name;

    private String email;

    private String password;

    private boolean isDeleted;

    private Role role;
}
