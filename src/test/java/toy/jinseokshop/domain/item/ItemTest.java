package toy.jinseokshop.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ItemTest {

    @Autowired EntityManager em;

    @Test
    void 책_테스트() {
        // given
        Item bookA = Book.createBook("우앙책", "aa", 10000, 100, "마이티진석", "진석출판사");

        // when
        em.persist(bookA);

        // then
        Item foundItem = em.find(Item.class, bookA.getId());
        Assertions.assertThat(bookA.getItemName()).isEqualTo(foundItem.getItemName());
    }

    @Test
    void 강의_테스트() {
        // given
        Item lectureA = Lecture.createLecture("우앙강의", "a", 60000, "마이티진석", "진석튜브");

        // when
        em.persist(lectureA);

        // then
        Item foundItem = em.find(Item.class, lectureA.getId());
        Assertions.assertThat(lectureA.getItemName()).isEqualTo(foundItem.getItemName());
    }

}