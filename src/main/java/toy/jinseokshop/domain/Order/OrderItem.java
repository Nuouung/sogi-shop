package toy.jinseokshop.domain.Order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import toy.jinseokshop.domain.item.Item;

import javax.persistence.*;

@Entity
@Getter @Setter(AccessLevel.PRIVATE)
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

    private int orderPrice; // 주문 가격
    private int orderQuantity; // 주문 수량

    public void setOrder(Order order) {
        this.order = order;
    }

    // == 기본 생성자를 호출하지 못하게 막음 == //
    protected OrderItem() {
    }

    // == 생성 메소드 == //
    public static OrderItem createOrderItem(Item item, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(item.getPrice() * count);
        orderItem.setOrderQuantity(count);

        item.reduceStockQuantity(count);
        return orderItem;
    }
}
