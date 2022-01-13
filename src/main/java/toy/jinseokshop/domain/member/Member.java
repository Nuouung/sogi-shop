package toy.jinseokshop.domain.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import toy.jinseokshop.domain.item.Item;
import toy.jinseokshop.web.member.MemberDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "member")
    private List<Item> items = new ArrayList<>();

    // member roles
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "MEMBER_ROLE", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    public Member(MemberDto memberDto) {
        this.email = memberDto.getEmail();
        this.password = memberDto.getPassword();
        this.address = new Address(memberDto.getAddressName(), memberDto.getRoadAddressName());
        this.isSeller = (memberDto.getIsSeller() == null) ? "NO" : "YES";
        this.roles.add(Role.GUEST);
        this.roles.add(Role.MEMBER);
        this.roles.add(Role.SELLER);
    }

    //==> 연관관계 메소드
    public void addItems(Item item) {
        items.add(item);
    }
}
