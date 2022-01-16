package toy.jinseokshop.domain.review;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import toy.jinseokshop.domain.Base;
import toy.jinseokshop.domain.item.Item;
import toy.jinseokshop.domain.member.Member;
import toy.jinseokshop.web.review.ReviewFormDto;

import javax.persistence.*;

@Entity
@Getter @Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends Base {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "REVIEW_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    private String content;
    private int stars;

    //==> 연관관계 메소드
    public void setItem(Item item) {
        this.item = item;
    }

    //==> 정적 펙토리 메소드
    public static Review createReview(ReviewFormDto reviewFormDto, Member member, Item item) {
        Review review = new Review();
        review.setMember(member);
        review.setItem(item);
        review.setContent(reviewFormDto.getContent());
        review.setStars(reviewFormDto.getStars());

        return review;
    }
}
