package toy.jinseokshop.domain.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toy.jinseokshop.domain.member.Member;
import toy.jinseokshop.domain.member.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public String login(String email, String password) {
        Optional<Member> foundMember = memberRepository.findByEmail(email);

        if (foundMember.isPresent()) {
            Member member = foundMember.get();
            if (member.getPassword().equals(password)) {
                return email;
            }
        }
        return null;
    }

}
