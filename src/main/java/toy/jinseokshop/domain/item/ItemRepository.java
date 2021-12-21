package toy.jinseokshop.domain.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    // c
    public Long save(Item item) {
        em.persist(item);
        return item.getItemId();
    }

    // r
    public Optional<Item> findByItemId(Long itemId) {
        return em.createQuery("select i from Item i where i.itemId = :itemId", Item.class)
                .setParameter("itemId", itemId)
                .getResultStream()
                .findAny();
    }

    public List<Item> findPage(int startPosition, int maxResult) {
        return em.createQuery("select i from Item i order by i.itemId desc", Item.class)
                .setFirstResult(startPosition)
                .setMaxResults(maxResult)
                .getResultList();
    }

    // u
    public void update(Long itemId, Item updateParam) {
        Item item = findByItemId(itemId).orElse(null);

        if (item == null) {
            return;
        }

        item.setItemName(updateParam.getItemName());
        item.setPrice(updateParam.getPrice());
        item.setStockQuantity(updateParam.getStockQuantity());
    }

    // d
    public void delete(Long itemId) {
        Item item = findByItemId(itemId).orElse(null);

        if (item == null) {
            return;
        }

        em.remove(item);
    }
}
