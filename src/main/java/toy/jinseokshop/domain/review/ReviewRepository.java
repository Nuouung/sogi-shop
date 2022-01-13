package toy.jinseokshop.domain.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy.jinseokshop.domain.item.Item;

import javax.persistence.EntityManager;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    private final EntityManager em;

    public void save(Review review) {
        em.persist(review);
    }

    public boolean findOneByItemId(Long itemId) {
        String query = "select r from Review r where r.item.id = :itemId";
        Optional<Review> review = em.createQuery(query, Review.class)
                .setParameter("itemId", itemId)
                .getResultStream()
                .findAny();
        return review.isPresent();
    }

    // 아이템 디테일 페이지에서 필요한 메소드
    public List<Review> findByItem(Item item) {
        String query = "select r from Review r join fetch r.item where r.item = :item";
        return em.createQuery(query, Review.class)
                .setParameter("item", item)
                .getResultList();
    }

    // 또한 아이템 디테일 페이지에서 필요한 메소드
    public ReviewGroupDto findOneUsingGroupBy(Long itemId) {
        String query = "select new toy.jinseokshop.domain.review.ReviewGroupDto" +
                "(r.item.id, COALESCE(AVG(r.stars), 0), COALESCE(COUNT(r.item.id), 0))" +
                " from Review r" +
                " where r.item.id = :itemId" +
                " group by r.item.id";

        return em.createQuery(query, ReviewGroupDto.class)
                .setParameter("itemId", itemId)
                .getResultStream().findAny()
                .orElse(null);
    }

    // 아이템 리스트 페이지에서 필요한 메소드
    public Map<Long, ReviewGroupDto> findPageUsingGroupBy(List<Long> itemIds) {
        String query = "select new toy.jinseokshop.domain.review.ReviewGroupDto" +
                "(r.item.id, COALESCE(AVG(r.stars), 0), COALESCE(COUNT(r.item.id), 0))" +
                " from Review r" +
                " where r.item.id = :itemId" +
                " group by r.item.id";

        return createResultMapWithIterator(itemIds, query);
    }

    private Map<Long, ReviewGroupDto> createResultMapWithIterator(List<Long> itemIds, String query) {
        Map<Long, ReviewGroupDto> resultMap = new HashMap<>();
        for (Long itemId : itemIds) {
            if (findOneByItemId(itemId)) {
                ReviewGroupDto reviewGroupDto = em.createQuery(query, ReviewGroupDto.class)
                        .setParameter("itemId", itemId)
                        .getSingleResult();
                resultMap.put(itemId, reviewGroupDto);
            }
        }
        return resultMap;
    }

}
