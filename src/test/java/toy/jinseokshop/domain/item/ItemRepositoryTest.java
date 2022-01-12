package toy.jinseokshop.domain.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import toy.jinseokshop.domain.member.Member;
import toy.jinseokshop.domain.review.ReviewService;
import toy.jinseokshop.web.item.ItemDto;
import toy.jinseokshop.web.member.MemberDto;
import toy.jinseokshop.web.review.ReviewFormDto;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ItemRepositoryTest {

    @Autowired EntityManager em;
    @Autowired ReviewService reviewService;
    @Autowired ItemRepository itemRepository;


    @BeforeEach
    void beforeEach() {
        insaneMethod();
    }

    @Test
    void insaneQueryTest() {
        // given
        // jpa는 where, having 절에만 서브쿼리를 제공한다. -> 아래 쿼리는 에러가 나는 쿼리
        String query = "select new toy.jinseokshop.domain.item.ItemListDto(i.id, i.itemName, i.price, COALESCE(x.rating, 0), COALESCE(x.numbers, 0))" +
                " from Item i" +
                " left outer join" +
                " (select r.item.id itemId, AVG(r.stars) rating, COUNT(r.item.id) numbers from Review r group by r.item.id) x" +
                " on i.id = x.itemId" +
                " order by desc";

        // when
//        List<ItemListDto> resultList = em.createQuery(query, ItemListDto.class)
//                .getResultList();

        // then

    }


    private void insaneMethod() {
        for (int i = 1; i < 20; i++) {
            // member 생성
            MemberDto memberDto = new MemberDto();
            memberDto.setEmail("member" + i);
            Member member = new Member(memberDto);
            em.persist(member);

            // item 생성
            ItemDto itemDto = new ItemDto();
            itemDto.setItemName("엄청난 강의다 이 말이야" + i);
            itemDto.setPrice(10000);
            itemDto.setStockQuantity(10);
            itemDto.setDType("L");
            itemDto.setOptionA("a");
            itemDto.setOptionB("b");
            Item item = Item.createItem(itemDto, new ArrayList<>());
            em.persist(item);

            for (int j = 1; j < 10; j++) {
                // review 생성
                ReviewFormDto reviewFormDto1 = new ReviewFormDto();
                reviewFormDto1.setContent("와 진짜 재밌다~" + j);
                reviewFormDto1.setStars(4); // 1 ~ 5
                reviewService.save(member.getEmail(), item.getId(), reviewFormDto1);
            }
        }
    }

}