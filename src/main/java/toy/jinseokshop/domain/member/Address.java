package toy.jinseokshop.domain.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import toy.jinseokshop.domain.Base;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String addressName;
    private String roadAddressName;

    public Address(@Nullable String addressName, @Nullable String roadAddressName) {
        this.addressName = addressName;
        this.roadAddressName = roadAddressName;
    }
}
