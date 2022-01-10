package toy.jinseokshop.domain.file;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import toy.jinseokshop.domain.item.Item;
import toy.jinseokshop.web.item.ItemDto;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class FileTest {

    @Autowired
    EntityManager em;
    private final ItemDto itemDto = new ItemDto();

    @BeforeEach
    void beforeEach() {
        ItemDto itemDto2 = new ItemDto();
        itemDto.setItemName("책");
        itemDto.setPrice(10000);
        itemDto.setStockQuantity(3);
        itemDto.setOptionA("아마 저자");
        itemDto.setOptionB("아마 출판사");
        itemDto.setDType("B");
        Item item = Item.createItem(itemDto, new ArrayList<>());
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