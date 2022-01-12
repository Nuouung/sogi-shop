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
import toy.jinseokshop.web.review.ReviewFormDto;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    public void save(String memberEmail, Long itemId, ReviewFormDto reviewFormDto) {
        // 1. 회원 객체를 얻는다
        Member member = (memberRepository.findByEmail(memberEmail).isPresent())? memberRepository.findByEmail(memberEmail).get() : null;

        // 2. 상품 객체를 얻는다
        Item item = (itemRepository.findById(itemId).isPresent())? itemRepository.findById(itemId).get() : null;

        // 그럴리는 없지만 만약 member 혹은 item이 null인 경우 대응할 수 있도록 코드를 짰다.
        if (member == null || item == null) {
            log.error(ErrorConst.WHY_ERROR_HAPPENED);
            return;
        }

        // 3. 지금까지 것들을 종합해서 review 객체를 만들고 db에 반영
        reviewRepository.save(Review.createReview(reviewFormDto, member, item));
    }

    public List<ReviewDto> findByItemId(Long itemId) {
        // 1. 아이템을 가져온다
        Item item = (itemRepository.findById(itemId).isPresent())? itemRepository.findById(itemId).get() : null;
        if (item == null) {
            log.error(ErrorConst.WHY_ERROR_HAPPENED);
            return null;
        }

        // 2. item과 연결되어 있는 Review를 끌어온다.
        List<Review> reviews = reviewRepository.findByItem(item);

        // 3. List 내의 모든 Review들을 ReviewDto로 변환한다.
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        for (Review review : reviews) {
            reviewDtoList.add(new ReviewDto(review.getMember().getEmail(), review.getContent(), review.getStars()));
        }
        return reviewDtoList;
    }

}
