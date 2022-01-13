package toy.jinseokshop.domain.item;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("E")
@Getter @Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Etcetera extends Item {

    //==> 정적 팩토리 메소드
    public static Etcetera createEtc(String itemName, String content, int price) {
        Etcetera etcetera = new Etcetera();
        etcetera.setItemName(itemName);
        etcetera.setContent(content);
        etcetera.setPrice(price);
        etcetera.setStockQuantity(ItemConst.SYMBOL_OF_ETC);
        return etcetera;
    }
}
