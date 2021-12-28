package toy.jinseokshop.fileUpload.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy.jinseokshop.fileUpload.domain.Music;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MusicRepository {

    private final EntityManager em;

    public void save(Music music) {
        em.persist(music);
    }

    public Music findById(Long id) {
        return em.find(Music.class, id);
    }
}
