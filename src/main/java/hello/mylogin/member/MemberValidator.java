package hello.mylogin.member;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MemberValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Member.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberDto member = (MemberDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email","required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password","required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name","required");

    }
}
