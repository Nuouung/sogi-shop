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

    public Optional<Item> findByItemName(String itemName) {
        return em.createQuery("select i from Item i where i.itemName = :itemName", Item.class)
                .setParameter("itemName", itemName)
                .getResultStream().findAny();
    }

    public Map<String, Object> findPage(int startPosition, int maxResult) {
        TypedQuery<Item> itemTypedQuery = em.createQuery("select i from Item i order by i.id desc", Item.class);
        List<Item> tempList = itemTypedQuery.getResultList();
        List<Item> resultList = itemTypedQuery
                .setFirstResult(startPosition)
                .setMaxResults(maxResult)
                .getResultList();

        return pagingManager.createPage(tempList, resultList);
    }

    public List<Long> findPageIds(int startPosition, int maxResult) {
        return em.createQuery("select i.id from Item i order by i.id desc", Long.class)
                .setFirstResult(startPosition)
                .setMaxResults(maxResult)
                .getResultList();
    }

}
