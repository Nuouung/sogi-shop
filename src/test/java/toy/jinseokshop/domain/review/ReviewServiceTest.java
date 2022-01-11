package toy.jinseokshop.domain.review;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import toy.jinseokshop.domain.item.Item;
import toy.jinseokshop.domain.item.ItemRepository;
import toy.jinseokshop.domain.member.Member;
import toy.jinseokshop.domain.member.MemberRepository;
import toy.jinseokshop.web.item.ItemDto;
import toy.jinseokshop.web.member.MemberDto;
import toy.jinseokshop.web.review.ReviewDto;

import javax.persistence.EntityManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ReviewServiceTest {

    @Autowired EntityManager em;
    @Autowired ReviewService reviewService;
    @Autowired ReviewRepository reviewRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired ItemRepository itemRepository;

    @BeforeEach
    void beforeEach() {
        // member 생성
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail("member");
        Member member = new Member(memberDto);
        em.persist(member);

        // item 생성
        ItemDto itemDto = new ItemDto();
        itemDto.setItemName("엄청난 강의다 이 말이야");
        itemDto.setPrice(10000);
        itemDto.setStockQuantity(10);
        itemDto.setDType("L");
        itemDto.setOptionA("a");
        itemDto.setOptionB("b");
        Item item =Item.createItem(itemDto, new ArrayList<>());
        em.persist(item);
    }

    @Test
    void reviewInsertTest() {
        // given
        Member member = memberRepository.findByEmail("member").get();
        Item item = itemRepository.findByItemName("엄청난 강의다 이 말이야").get();
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setContent("와 진짜 재밌다~");
        reviewDto.setStars(9); // 1 ~ 10

        // when
        reviewService.save(member.getEmail(), item.getId(), reviewDto);
        Review review = reviewRepository.findByItem(item).get(0);

        // then
        Assertions.assertThat(review.getItem().getItemName()).isEqualTo("엄청난 강의다 이 말이야");
    }

    @Test
    void reviewInsertFailTest() {
        // given
        Member member = memberRepository.findByEmail("member").get();

        Item item = itemRepository.findByItemName("엄청난 강의다 이 말이야").get();

        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setContent("와 진짜 재밌다~");
        reviewDto.setStars(9); // 1 ~ 10

        // when
        reviewService.save(member.getEmail(), -1L, reviewDto);
        reviewService.save("이건 없을거야 얏호", item.getId(), reviewDto);

        // then
        Assertions.assertThat(reviewRepository.findByItem(item)).isEmpty();
    }

}