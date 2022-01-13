package toy.jinseokshop.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import toy.jinseokshop.domain.item.Item;
import toy.jinseokshop.domain.item.ItemConst;

import javax.persistence.*;

@Entity
@Getter @Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    private int orderPrice;
    private int orderItemQuantity;

    //==> 연관 관계 메소드
    public void setOrder(Order order) {
        this.order = order;
    }
    
    //==> 정적 생성 메소드
    public static OrderItem createOrderItem(Item item, int orderItemQuantity) {
        OrderItem orderItem = new OrderItem();
        int stockQuantity = item.getStockQuantity();
        orderItem.setItem(item);
        // 수량이 있는지 없는지에 따라 다르게 계산하는 메소드
        storePriceAndQuantity(item, orderItem, stockQuantity, orderItemQuantity);
        return orderItem;
    }

    private static void storePriceAndQuantity(Item item, OrderItem orderItem, int stockQuantity, int orderItemQuantity) {
        if (stockQuantity == ItemConst.SYMBOL_OF_LECTURE || stockQuantity == ItemConst.SYMBOL_OF_ETC) {
            // LECTURE or ETC
            orderItem.setOrderPrice(item.getPrice());
            orderItem.setOrderItemQuantity(-1);
        } else {
            // BOOK
            orderItem.setOrderPrice(item.getPrice() * orderItemQuantity);
            orderItem.setOrderItemQuantity(item.getStockQuantity());
        }
    }
}
