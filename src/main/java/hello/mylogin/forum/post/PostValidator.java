package hello.mylogin.forum.post;

import hello.mylogin.member.MemberDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PostValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Post.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Post post = (Post) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title","required");

    }
}
