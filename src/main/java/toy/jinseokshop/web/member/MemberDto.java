package toy.jinseokshop.web.member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberDto {

    private Long id;

    private String email;
    private String password;

    private String addressName;
    private String roadAddressName;

    private int passion;

    private String isSeller;

}
