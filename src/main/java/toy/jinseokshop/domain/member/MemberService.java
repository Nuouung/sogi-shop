package toy.jinseokshop.domain.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public String login(String userId, String password) {
        Optional<Member> findMember = memberRepository.findByUserId(userId);

        if (!findMember.isEmpty()) {
            Member member = findMember.get();
            if (member.getPassword().equals(password)) {
                return userId;
            }
        }
        return null;
    }
}
