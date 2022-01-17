package toy.jinseokshop.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.jinseokshop.web.member.MemberDto;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberDto findMemberDtoByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isEmpty()) {
            return null;
        }

        Member foundMember = member.get();
        return createAndStoreMemberDto(foundMember);
    }

    public void chargePassion(Long id) {
        Optional<Member> member = memberRepository.findById(id);

        if (member.isEmpty()) {
            return;
        }

        Member target = member.get();
        int updatedPassion = target.getPassion() + MemberConst.JOIN_PASSION;
        target.updatePassion(updatedPassion);
    }

    public String findPassword(String email) {
        Optional<Member> foundMember = memberRepository.findByEmail(email);
        if (foundMember.isEmpty()) {
            return MemberConst.NO_JOINED_MEMBER;
        }
        return foundMember.get().getPassword();
    }

    private MemberDto createAndStoreMemberDto(Member foundMember) {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(foundMember.getId());
        memberDto.setEmail(foundMember.getEmail());
        memberDto.setPassword(foundMember.getPassword());
        memberDto.setIsSeller(foundMember.getIsSeller());
        memberDto.setPassion(foundMember.getPassion());
        return memberDto;
    }

}
