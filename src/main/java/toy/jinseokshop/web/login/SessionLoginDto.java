package toy.jinseokshop.web.login;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SessionLoginDto {

    private String email;
    private String isSeller;

    public SessionLoginDto(String email, String isSeller) {
        this.email = email;
        this.isSeller = isSeller;
    }
}