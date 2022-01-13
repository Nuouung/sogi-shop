package toy.jinseokshop.domain.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import toy.jinseokshop.domain.item.Item;
import toy.jinseokshop.domain.member.Member;
import toy.jinseokshop.web.item.ItemFormDto;
import toy.jinseokshop.web.member.MemberDto;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @MockBean BindingResult bindingResult;

    @Test
    void orderOperateTest() {
        // given
        // 회원
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail("jin6106");
        memberDto.setPassword("123456");
        memberDto.setRoadAddressName("여기 어디");
        memberDto.setAddressName("나는 누구");
        memberDto.setIsSeller("Y");
        Member member = new Member(memberDto);
        em.persist(member);

        // 상품
        ItemFormDto itemFormDto = new ItemFormDto();
        itemFormDto.setDType("B");
        itemFormDto.setItemName("이츠 북");
        itemFormDto.setContent("냐냐냐냐냐");
        itemFormDto.setPrice(23000);
        itemFormDto.setStockQuantity(30);
        itemFormDto.setOptionA("이진석");
        itemFormDto.setOptionB("진석출판사");
        Item item = Item.createItem(member, itemFormDto, new ArrayList<>());
        em.persist(item);

        ItemFormDto itemFormDto2 = new ItemFormDto();
        itemFormDto2.setDType("L");
        itemFormDto2.setItemName("이츠 강의");
        itemFormDto2.setContent("냐냐냐냐냐");
        itemFormDto2.setPrice(13000);
        itemFormDto2.setStockQuantity(3);
        itemFormDto2.setOptionA("이진석");
        itemFormDto2.setOptionB("진석프런");
        Item item2 = Item.createItem(member, itemFormDto2, new ArrayList<>());
        em.persist(item2);

        // 상품 아이디 리스트
        List<Long> itemIds = new ArrayList<>();
        itemIds.add(item.getId());
        itemIds.add(item2.getId());

        // 주문 수량
        List<Integer> orderItemQuantities = new ArrayList<>();
        orderItemQuantities.add(3);
        orderItemQuantities.add(0);

        // when
        Long orderId = orderService.save(member.getEmail(), itemIds, orderItemQuantities, bindingResult);

        // then
    }

}