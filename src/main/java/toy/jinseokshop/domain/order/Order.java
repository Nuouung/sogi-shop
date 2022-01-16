package toy.jinseokshop.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import toy.jinseokshop.domain.Base;
import toy.jinseokshop.domain.member.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ORDERS")
public class Order extends Base {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ORDERS_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    //==> 연관관계 메소드
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    //==> 정적 펙토리 메소드
    public static Order createOrder(Member member, Delivery delivery, List<OrderItem> orderItems, int totalPrice) {
        Order order = new Order();
        order.setMember(member);

        // 회원 열정 상품 가격의 총합만큼 차감
        member.reducePassion(totalPrice);

        order.setDelivery(delivery);
        if (!orderItems.isEmpty()) {
            for (OrderItem orderItem : orderItems) {
                order.addOrderItem(orderItem);
            }
        }
        order.setOrderStatus(OrderStatus.COMP);
        return order;
    }
}
