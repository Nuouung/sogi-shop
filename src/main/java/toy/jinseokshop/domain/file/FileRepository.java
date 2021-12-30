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

    public File findByBoardId(Long boardId) {
        return em.createQuery("select f from File f where f.board.boardId = :boardId", File.class)
                .setParameter("boardId", boardId)
                .getSingleResult();
    }

    public File findByBoardIdWithJoin(Long boardId) {
        return em.createQuery("select f from File f join fetch f.files where f.board.boardId = :boardId", File.class)
                .setParameter("boardId", boardId)
                .getSingleResult();
    }
}
