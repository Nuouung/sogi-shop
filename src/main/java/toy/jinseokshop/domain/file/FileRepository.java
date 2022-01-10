package toy.jinseokshop.domain.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class FileRepository {

    private final EntityManager em;

    public void save(File file) {
        em.persist(file);
    }

    public File findByItemId(Long itemId) {
        return em.createQuery("select f from File f where f.item.id = :itemId", File.class)
                .setParameter("itemId", itemId)
                .getSingleResult();
    }
}
