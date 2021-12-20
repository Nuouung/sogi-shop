package toy.jinseokshop.web.validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import toy.jinseokshop.domain.member.Member;
import toy.jinseokshop.domain.member.MemberRepository;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class MemberValidator {

    private final MemberRepository memberRepository;

    public boolean supports(Class<?> clazz) {
        return Member.class.isAssignableFrom(clazz);
    }

    public void joinValidate(Member member, BindingResult bindingResult) {

        Optional<Member> findMember = memberRepository.findByUserId(member.getUserId());

        // 검증 로직
        // 1. 아이디는 null이어서는 안된다. 아이디의 길이는 10을 초과할 수 없다.
        // 2. 비밀번호는 null이어서는 안된다. 비밀번호의 길이는 6 이상이어야 한다.
        // 3. 아이디는 중복되어서는 안된다.
        if (!StringUtils.hasText(member.getUserId()) || member.getUserId().length() > 10) {
            bindingResult.addError(new FieldError("member", "userId", "아이디는 공백이거나 10글자를 초과할 수 없습니다!"));
//            bindingResult.addError(new FieldError("member", "userId", member.getUserId(), false, new String[]{"range.member.userId"}, null, null));
        }

        if (member.getPassword() == null || member.getPassword().length() < 6) {
            bindingResult.addError(new FieldError("member", "password", "비밀번호는 6글자 이상으로 입력해 주십시오!"));
//            bindingResult.addError(new FieldError("member", "password", member.getPassword(), false, new String[]{"range.member.password"}, null, null));
        }

        if (!findMember.isEmpty()) {
            bindingResult.addError(new ObjectError("member", "이미 존재하는 회원입니다. 아이디를 바꿔용."));
//            bindingResult.addError(new ObjectError("member", new String[]{"duplicate.member"}, null, null));
        }

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
        }
    }
}
