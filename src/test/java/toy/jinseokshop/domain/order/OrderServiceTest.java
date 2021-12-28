package toy.jinseokshop.domain.order;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import toy.jinseokshop.domain.item.Item;
import toy.jinseokshop.domain.item.ItemRepository;
import toy.jinseokshop.domain.member.Member;

import javax.persistence.EntityManager;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@Rollback(value = false)
class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired ItemRepository itemRepository;
    @Autowired EntityManager em;

    @Test
    void saveOrderTest() {
        // given
        Member member = new Member();
        member.setUserId("진석");
        member.setPassword("123456");

        Item item = new Item();
        item.setItemName("맛있는 아메리카노");
        item.setPrice(10000);
        item.setStockQuantity(100);

        em.persist(member);
        Long itemId = itemRepository.save(item);

        // when
        orderService.order(member.getUserId(), itemId, 3);

        // then
    }

}