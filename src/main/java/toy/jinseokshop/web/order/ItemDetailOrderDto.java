package toy.jinseokshop.web.order;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemDetailOrderDto {

    private String email;
    private Long itemId;
    private int orderItemQuantity;
    private String category;

}
