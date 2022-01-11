package toy.jinseokshop.domain.review;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.jinseokshop.domain.error.ErrorConst;
import toy.jinseokshop.domain.item.Item;
import toy.jinseokshop.domain.item.ItemRepository;
import toy.jinseokshop.domain.member.Member;
import toy.jinseokshop.domain.member.MemberRepository;
import toy.jinseokshop.web.review.ReviewDto;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    public void save(String memberEmail, Long itemId, ReviewDto reviewDto) {
        // 1. 회원 객체를 얻는다
        Member member = (memberRepository.findByEmail(memberEmail).isPresent())? memberRepository.findByEmail(memberEmail).get() : null;

        // 2. 상품 객체를 얻는다
        Item item = (itemRepository.findById(itemId).isPresent())? itemRepository.findById(itemId).get() : null;

        if (member == null || item == null) {
            log.error(ErrorConst.WHY_ERROR_HAPPENED);
            return;
        }

        // 3. 지금까지 것들을 종합해서 review 객체를 만들고 db에 반영
        reviewRepository.save(Review.createReview(reviewDto, member, item));
    }

}
