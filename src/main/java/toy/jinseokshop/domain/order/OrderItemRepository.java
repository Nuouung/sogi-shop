package toy.jinseokshop.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("select o from OrderItem o where o.order.id = :orderId")
    List<OrderItem> findAllByOrderId(@Param("orderId") Long orderId);
}
