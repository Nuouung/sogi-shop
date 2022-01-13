package toy.jinseokshop.domain.file;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import toy.jinseokshop.domain.item.Item;
import toy.jinseokshop.domain.member.Member;
import toy.jinseokshop.web.item.ItemFormDto;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.UUID;

@SpringBootTest
@Transactional
@Rollback(value = false)
class FileTest {

    @Autowired
    EntityManager em;
    private final ItemFormDto itemFormDto = new ItemFormDto();

    @BeforeEach
    void beforeEach() {
        ItemFormDto itemFormDto2 = new ItemFormDto();
        itemFormDto.setItemName("책");
        itemFormDto.setPrice(10000);
        itemFormDto.setStockQuantity(3);
        itemFormDto.setOptionA("아마 저자");
        itemFormDto.setOptionB("아마 출판사");
        itemFormDto.setDType("B");
        Item item = Item.createItem(new Member(), itemFormDto, new ArrayList<>());
        em.persist(item);

        em.flush();
        em.clear();
    }

    @Test
    void fileInsertTest() {
        File file = new File("helloSpring.pdf", UUID.randomUUID() + ".pdf");
        Item item = em.find(Item.class, 1L);
        Item updateItem = Item.updateFile(item, file);
        em.persist(file);
        Assertions.assertThat(updateItem.getId()).isEqualTo(1L);
        Assertions.assertThat(file.getItem().getId()).isEqualTo(1L);
    }

}