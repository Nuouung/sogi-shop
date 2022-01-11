package toy.jinseokshop.domain.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import toy.jinseokshop.web.member.MemberDto;

import javax.persistence.*;

@Entity
@Getter @Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String email;
    private String password;

    @Embedded
    private Address address;

    private String isSeller;
    private String memberType; // admin, guest, member

    public Member(MemberDto memberDto) {
        this.email = memberDto.getEmail();
        this.password = memberDto.getPassword();
        this.address = new Address(memberDto.getAddressName(), memberDto.getRoadAddressName());
        this.isSeller = (memberDto.getIsSeller() == null) ? "NO" : "YES";
        this.memberType = MemberConst.MEMBER;
    }
}
