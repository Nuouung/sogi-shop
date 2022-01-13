package toy.jinseokshop.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.jinseokshop.domain.review.Review;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
