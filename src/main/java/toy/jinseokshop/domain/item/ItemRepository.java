package toy.jinseokshop.domain.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy.jinseokshop.domain.paging.PagingManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;
    private final PagingManager<Item> pagingManager;

    public void save(Item item) {
        em.persist(item);
    }

    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(em.find(Item.class, id));
    }

    public Map<String, Object> findPage(int startPosition, int maxResult) {
        // TODO 이름, 가격, 리뷰 수, 별점 찍어줘야 함. (아직 리뷰가 개발되지 않았기 때문에)
        TypedQuery<Item> itemTypedQuery = em.createQuery("select i from Item i order by i.id desc", Item.class);
        List<Item> tempList = itemTypedQuery.getResultList();
        List<Item> resultList = itemTypedQuery
                .setFirstResult(startPosition)
                .setMaxResults(maxResult)
                .getResultList();

        return pagingManager.createPage(tempList, resultList);
    }
}
