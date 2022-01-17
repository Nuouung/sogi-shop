package toy.jinseokshop.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public Long save(Order order) {
        em.persist(order);
        return order.getId();
    }

    public List<Order> findAllByMemberId(Long memberId) {
        String query = "select o from Order o where o.member.id = :memberId";
        return em.createQuery(query, Order.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

}
