package toy.jinseokshop.domain.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy.jinseokshop.domain.item.Item;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    private final EntityManager em;

    public void save(Review review) {
        em.persist(review);
    }

    public List<Review> findByItem(Item item) {
        String query = "select r from Review r join fetch r.item where r.item = :item";
        return em.createQuery(query, Review.class)
                .setParameter("item", item)
                .getResultList();
    }

}
