package toy.jinseokshop.domain.Order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter(AccessLevel.PRIVATE)
public class Delivery {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "DELiVERY_ID")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    public void setOrder(Order order) {
        this.order = order;
    }

    // == 생성 메소드 == //
    public static Delivery createDelivery() {
        Delivery delivery = new Delivery();
        delivery.setDeliveryStatus(DeliveryStatus.COMP);
        return delivery;
    }
}
